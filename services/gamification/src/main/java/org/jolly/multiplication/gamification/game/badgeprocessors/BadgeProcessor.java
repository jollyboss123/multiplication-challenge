package org.jolly.multiplication.gamification.game.badgeprocessors;

import org.jolly.multiplication.gamification.challenge.ChallengeSolvedEvent;
import org.jolly.multiplication.gamification.game.domain.BadgeType;
import org.jolly.multiplication.gamification.game.domain.ScoreCard;

import java.util.List;
import java.util.Optional;

/**
 * @author jolly
 */
public interface BadgeProcessor {
    /**
     * Processes and decides if the user is entitled to a badge.
     *
     * @return a {@link BadgeType} if the user is entitled to this badge, otherwise empty
     */
    Optional<BadgeType> process(int currentScore, List<ScoreCard> scoreCardList, ChallengeSolvedEvent challenge);

    /**
     * @return the {@link BadgeType} that this processor is handling. You can use it
     * to filter processors according to your needs.
     */
    BadgeType badgeType();
}
