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
public class GoldBadgeProcessor implements BadgeProcessor {
    @Override
    public Optional<BadgeType> process(int currentScore, List<ScoreCard> scoreCardList, ChallengeSolvedEvent challenge) {
        if (currentScore > 400) {
            return Optional.of(BadgeType.GOLD);
        }
        return Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return BadgeType.GOLD;
    }
}
