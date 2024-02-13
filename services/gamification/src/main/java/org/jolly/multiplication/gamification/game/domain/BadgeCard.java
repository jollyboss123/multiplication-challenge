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
public class BadgeCard {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    @EqualsAndHashCode.Exclude
    private long timestamp;
    private BadgeType badgeType;

    public BadgeCard(final Long userId, final BadgeType badgeType) {
        this(null, userId, System.currentTimeMillis(), badgeType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BadgeCard badgeCard = (BadgeCard) o;
        return Objects.equals(id, badgeCard.id) && Objects.equals(userId, badgeCard.userId) && badgeType == badgeCard.badgeType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, badgeType);
    }
}
