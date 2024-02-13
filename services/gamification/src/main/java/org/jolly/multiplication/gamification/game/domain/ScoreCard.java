package org.jolly.multiplication.gamification.game.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Objects;

/**
 * @author jolly
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ScoreCard {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private Long attemptId;
    @EqualsAndHashCode.Exclude
    private Long timestamp;
    private int score;

    private static final int DEFAULT_SCORE = 10;

    public ScoreCard(final Long userId, final Long attemptId) {
        this(null, userId, attemptId, System.currentTimeMillis(), DEFAULT_SCORE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScoreCard scoreCard = (ScoreCard) o;
        return score == scoreCard.score
                && Objects.equals(id, scoreCard.id)
                && Objects.equals(userId, scoreCard.userId)
                && Objects.equals(attemptId, scoreCard.attemptId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, attemptId, score);
    }
}
