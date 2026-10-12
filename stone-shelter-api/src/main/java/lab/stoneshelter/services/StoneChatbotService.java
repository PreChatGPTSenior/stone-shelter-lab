package lab.stoneshelter.services;

import java.util.UUID;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import lab.stoneshelter.exceptions.StoneChatUnavailableException;
import lab.stoneshelter.entities.StoneChatTurnEntity;
import lab.stoneshelter.mappers.dtos.StoneChatConversationEntityToStoneChatHistoryResponseMapper;
import lab.stoneshelter.mappers.dtos.StoneChatTurnEntityToStoneChatMessageResponseMapper;
import lab.stoneshelter.mappers.entities.StoneChatMessageRequestToStoneChatTurnEntityMapper;
import lab.stoneshelter.repositories.StoneChatConversationRepository;
import lab.stoneshelter.shared.StoneChatHistoryResponse;
import lab.stoneshelter.shared.StoneChatMessageRequest;
import lab.stoneshelter.shared.StoneChatMessageResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StoneChatbotService {
    private record Components(StoneChatConversationRepository history, StoneChatConversationSerializationService serialization,
                              StoneChatAdmissionService admission, StoneChatAvailabilityService availability) {}
    private record Mapping(StoneChatMessageRequestToStoneChatTurnEntityMapper request, StoneChatTurnEntityToStoneChatMessageResponseMapper response,
                           StoneChatConversationEntityToStoneChatHistoryResponseMapper history, StoneChatContextSelector context) {}
    private record Execution(StoneExactSearchService search, Clock clock, Duration deadline) {}
    private Execution execution;
    @Autowired public void configureExactSearch(StoneExactSearchService search, Clock clock, StoneChatSettings settings) {
        execution = new Execution(search, clock, settings.deadline());
    }
    private final Components components;
    private final Mapping mapping;
    public StoneChatbotService(StoneChatConversationRepository history, StoneChatConversationSerializationService serialization,
            StoneChatAdmissionService admission, StoneChatAvailabilityService availability, StoneChatMessageRequestToStoneChatTurnEntityMapper requestMapper,
            StoneChatTurnEntityToStoneChatMessageResponseMapper responseMapper, StoneChatConversationEntityToStoneChatHistoryResponseMapper historyMapper,
            StoneChatContextSelector contextSelector) {
        components = new Components(history, serialization, admission, availability);
        mapping = new Mapping(requestMapper, responseMapper, historyMapper, contextSelector);
    }
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public StoneChatMessageResponse sendMessage(StoneChatMessageRequest request, String sessionId, Runnable onCommitted) {
        return components.availability().execute(() -> {
            components.admission().authorize(sessionId, request.conversationId(), true);
            StoneChatTurnEntity turn = mapping.request().toEntity(request);
            StoneChatTurnEntity replay = components.history().findCommitted(components.history().findById(sessionId, request.conversationId()), turn);
            if (replay != null) { components.availability().assertReady(); return mapping.response().toDto(replay); }
            StoneChatTurnEntity committed;
            Instant processingDeadline = execution.clock().instant().plus(execution.deadline());
            try (var guard = components.serialization().acquire(sessionId, request.conversationId())) {
                components.availability().assertReady();
                var conversation = components.history().findById(sessionId, request.conversationId());
                StoneChatTurnEntity racedReplay = components.history().findCommitted(conversation, turn);
                if (racedReplay != null) return mapping.response().toDto(racedReplay);
                if (mapping.context().select(conversation.turns()) == null) throw new StoneChatUnavailableException();
                guard.check(); components.availability().assertReady();
                CompletableFuture<StoneChatTurnEntity> completion = new CompletableFuture<>();
                Thread worker = Thread.ofVirtual().name("exact-search-turn").start(() -> {
                    try {
                        StoneChatTurnEntity completed = execution.search().complete(turn, conversation.turns(), processingDeadline);
                        guard.check(); components.availability().assertReady();
                        StoneChatTurnEntity saved = components.history().append(sessionId, request.conversationId(), guard.token(), completed);
                        guard.check(); components.availability().assertReady(); completion.complete(saved);
                    } catch (RuntimeException exception) { completion.completeExceptionally(exception); }
                });
                try {
                    committed = completion.get(Math.max(1, Duration.between(execution.clock().instant(), processingDeadline).toNanos()), TimeUnit.NANOSECONDS);
                } catch (InterruptedException exception) {
                    worker.interrupt(); Thread.currentThread().interrupt(); throw new StoneChatUnavailableException(exception);
                } catch (TimeoutException exception) {
                    worker.interrupt(); throw new StoneChatUnavailableException(exception);
                } catch (ExecutionException exception) {
                    if (exception.getCause() instanceof RuntimeException runtime) throw runtime;
                    throw new StoneChatUnavailableException(exception);
                }
                guard.check(); components.availability().assertReady();
            }
            components.availability().assertReady(); onCommitted.run(); return mapping.response().toDto(committed);
        });
    }
    @Transactional(readOnly = true)
    public StoneChatHistoryResponse findById(UUID conversationId, String sessionId) {
        return components.availability().execute(() -> {
            components.admission().authorize(sessionId, conversationId, false);
            StoneChatHistoryResponse response = mapping.history().toDto(components.history().findById(sessionId, conversationId));
            components.availability().assertReady(); return response;
        });
    }
}
