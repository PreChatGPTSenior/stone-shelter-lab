package lab.stoneshelter.entities;

import tools.jackson.databind.JsonNode;

public class OutboxDeadLetterEntity {
    private JsonNode originalEvent;
    private String failureStage;
    private String failureReason;
    private int attemptCount;
    private String originalTopic;

    public OutboxDeadLetterEntity() {
    }

    public JsonNode getOriginalEvent() { return originalEvent; }
    public void setOriginalEvent(JsonNode originalEvent) { this.originalEvent = originalEvent; }
    public String getFailureStage() { return failureStage; }
    public void setFailureStage(String failureStage) { this.failureStage = failureStage; }
    public String getFailureReason() { return failureReason; }
    public void setFailureReason(String failureReason) { this.failureReason = failureReason; }
    public int getAttemptCount() { return attemptCount; }
    public void setAttemptCount(int attemptCount) { this.attemptCount = attemptCount; }
    public String getOriginalTopic() { return originalTopic; }
    public void setOriginalTopic(String originalTopic) { this.originalTopic = originalTopic; }
}
