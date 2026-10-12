package lab.stoneshelter.criteria;

import java.time.Instant;

public record StoneDocumentEmbeddingCriteria(String text, boolean query, Instant deadline) {
}
