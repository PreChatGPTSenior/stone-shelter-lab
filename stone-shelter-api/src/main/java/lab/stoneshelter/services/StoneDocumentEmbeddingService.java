package lab.stoneshelter.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lab.stoneshelter.criteria.StoneDocumentEmbeddingCriteria;
import lab.stoneshelter.entities.StoneDocumentEmbeddingEntity;
import lab.stoneshelter.mappers.entities.StoneDocumentEmbeddingCriteriaToStoneDocumentEmbeddingInputEntityMapper;
import lab.stoneshelter.repositories.OllamaEmbeddingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StoneDocumentEmbeddingService {
    private static final Pattern TOKEN_PART = Pattern.compile("[\\p{L}\\p{N}]+|[^\\s]");
    private final OllamaEmbeddingRepository ollamaEmbeddingRepository;
    private final StoneDocumentEmbeddingCriteriaToStoneDocumentEmbeddingInputEntityMapper
            stoneDocumentEmbeddingCriteriaToStoneDocumentEmbeddingInputEntityMapper;

    public StoneDocumentEmbeddingService(OllamaEmbeddingRepository ollamaEmbeddingRepository,
            StoneDocumentEmbeddingCriteriaToStoneDocumentEmbeddingInputEntityMapper
                    stoneDocumentEmbeddingCriteriaToStoneDocumentEmbeddingInputEntityMapper) {
        this.ollamaEmbeddingRepository = ollamaEmbeddingRepository;
        this.stoneDocumentEmbeddingCriteriaToStoneDocumentEmbeddingInputEntityMapper =
                stoneDocumentEmbeddingCriteriaToStoneDocumentEmbeddingInputEntityMapper;
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public StoneDocumentEmbeddingEntity embed(StoneDocumentEmbeddingCriteria stoneDocumentEmbeddingCriteria) {
        if (stoneDocumentEmbeddingCriteria == null || stoneDocumentEmbeddingCriteria.text() == null
                || stoneDocumentEmbeddingCriteria.text().isBlank() || stoneDocumentEmbeddingCriteria.deadline() == null) {
            throw new IllegalArgumentException("Embedding text and deadline are required.");
        }
        return ollamaEmbeddingRepository.embed(
                stoneDocumentEmbeddingCriteriaToStoneDocumentEmbeddingInputEntityMapper.toEntity(stoneDocumentEmbeddingCriteria));
    }

    public int estimateTokens(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Token estimation requires text.");
        }
        Matcher matcher = TOKEN_PART.matcher(text);
        int tokens = 0;
        while (matcher.find()) {
            tokens += (matcher.end() - matcher.start() + 3) / 4;
        }
        return tokens;
    }
}
