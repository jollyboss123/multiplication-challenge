package org.jolly.multiplication.gamification.game.badgeprocessors;

import org.jolly.multiplication.gamification.challenge.ChallengeSolvedEvent;
import org.jolly.multiplication.gamification.game.domain.BadgeType;
import org.jolly.multiplication.gamification.game.domain.ScoreCard;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author jolly
 */
@Component
public class LuckyNumberBadgeProcessor implements BadgeProcessor {
    private static final int LUCKY_NUMBER = 42;

    @Override
    public Optional<BadgeType> process(int currentScore, List<ScoreCard> scoreCardList, ChallengeSolvedEvent challenge) {
        if (LUCKY_NUMBER == challenge.getFactorA() || LUCKY_NUMBER == challenge.getFactorB()) {
            return Optional.of(BadgeType.LUCKY_NUMBER);
        }
        return Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return BadgeType.LUCKY_NUMBER;
    }
}
