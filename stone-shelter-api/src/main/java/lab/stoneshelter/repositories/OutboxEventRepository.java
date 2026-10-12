package lab.stoneshelter.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;
import lab.stoneshelter.entities.OutboxEventEntity;
import lab.stoneshelter.enums.OutboxDeliveryState;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.json.JsonMapper;

@Repository
public class OutboxEventRepository {
    private final JdbcTemplate jdbcTemplate;
    private final JsonMapper jsonMapper = JsonMapper.builder().build();

    public OutboxEventRepository(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    public OutboxEventEntity save(OutboxEventEntity outboxEventEntity) {
        jdbcTemplate.update("""
                INSERT INTO outbox_event
                    (event_id, event_type, schema_version, occurred_at, payload, created_at, delivery_state)
                VALUES (?, ?, ?, ?, ?::jsonb, ?, ?)
                ON CONFLICT (event_id) DO NOTHING
                """, outboxEventEntity.getEventId(), outboxEventEntity.getEventType(), outboxEventEntity.getSchemaVersion(),
                OffsetDateTime.ofInstant(outboxEventEntity.getOccurredAt(), ZoneOffset.UTC),
                jsonMapper.writeValueAsString(outboxEventEntity.getPayload()),
                OffsetDateTime.ofInstant(outboxEventEntity.getCreatedAt(), ZoneOffset.UTC),
                outboxEventEntity.getDeliveryState().name());
        return findById(outboxEventEntity.getEventId())
                .orElseThrow(() -> new IllegalStateException("The persisted outbox event is missing."));
    }

    public Optional<OutboxEventEntity> findById(UUID eventId) {
        return jdbcTemplate.query("""
                SELECT event_id, event_type, schema_version, occurred_at, payload, created_at, delivery_state
                FROM outbox_event WHERE event_id = ?
                """, this::mapEntity, eventId).stream().findFirst();
    }

    private OutboxEventEntity mapEntity(ResultSet resultSet, int row) throws SQLException {
        OutboxEventEntity outboxEventEntity = new OutboxEventEntity();
        outboxEventEntity.setEventId(resultSet.getObject("event_id", UUID.class));
        outboxEventEntity.setEventType(resultSet.getString("event_type"));
        outboxEventEntity.setSchemaVersion(resultSet.getInt("schema_version"));
        outboxEventEntity.setOccurredAt(resultSet.getObject("occurred_at", OffsetDateTime.class).toInstant());
        outboxEventEntity.setPayload(jsonMapper.readTree(resultSet.getString("payload")));
        outboxEventEntity.setCreatedAt(resultSet.getObject("created_at", OffsetDateTime.class).toInstant());
        outboxEventEntity.setDeliveryState(OutboxDeliveryState.valueOf(resultSet.getString("delivery_state")));
        return outboxEventEntity;
    }
}
