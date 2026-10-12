package lab.stoneshelter.entities;

import java.util.List;
import java.util.UUID;
import lab.stoneshelter.criteria.StoneExactSearchCriteria;

public class StoneChatTurnEntity {
    private StoneExactSearchCriteria exactSearchCriteria;
    public StoneExactSearchCriteria getExactSearchCriteria() { return exactSearchCriteria; }
    public void setExactSearchCriteria(StoneExactSearchCriteria value) { exactSearchCriteria = value; }
    private UUID turnId;
    private String message;
    private StoneChatContextEntity context;
    private String text;
    private List<StoneChatStoneEntity> stones;
    public StoneChatTurnEntity() {}
    public UUID getTurnId() { return turnId; }
    public void setTurnId(UUID turnId) { this.turnId = turnId; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public StoneChatContextEntity getContext() { return context; }
    public void setContext(StoneChatContextEntity context) { this.context = context; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public List<StoneChatStoneEntity> getStones() { return stones; }
    public void setStones(List<StoneChatStoneEntity> stones) { this.stones = stones; }
}
