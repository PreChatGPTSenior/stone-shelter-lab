package lab.stoneshelter.entities;

import java.time.Instant;
import java.util.UUID;
import lab.stoneshelter.enums.OutboxDeliveryState;
import tools.jackson.databind.JsonNode;

public class OutboxEventEntity {
    private UUID eventId;
    private String eventType;
    private Integer schemaVersion;
    private Instant occurredAt;
    private JsonNode payload;
    private Instant createdAt;
    private OutboxDeliveryState deliveryState;

    public OutboxEventEntity() {
    }

    public UUID getEventId() { return eventId; }
    public void setEventId(UUID eventId) { this.eventId = eventId; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public Integer getSchemaVersion() { return schemaVersion; }
    public void setSchemaVersion(Integer schemaVersion) { this.schemaVersion = schemaVersion; }
    public Instant getOccurredAt() { return occurredAt; }
    public void setOccurredAt(Instant occurredAt) { this.occurredAt = occurredAt; }
    public JsonNode getPayload() { return payload; }
    public void setPayload(JsonNode payload) { this.payload = payload; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public OutboxDeliveryState getDeliveryState() { return deliveryState; }
    public void setDeliveryState(OutboxDeliveryState deliveryState) { this.deliveryState = deliveryState; }
}
