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
public class BronzeBadgeProcessor implements BadgeProcessor {
    @Override
    public Optional<BadgeType> process(final int currentScore, final List<ScoreCard> scoreCardList, final ChallengeSolvedEvent challenge) {
        if (currentScore > 50) {
            return Optional.of(BadgeType.BRONZE);
        }
        return Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return BadgeType.BRONZE;
    }
}
