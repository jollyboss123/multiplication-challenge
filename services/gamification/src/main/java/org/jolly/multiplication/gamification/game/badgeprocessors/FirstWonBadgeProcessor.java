package org.jolly.multiplication.gamification.game.badgeprocessors;

import org.jolly.multiplication.gamification.challenge.ChallengeSolvedDTO;
import org.jolly.multiplication.gamification.game.domain.BadgeType;
import org.jolly.multiplication.gamification.game.domain.ScoreCard;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author jolly
 */
@Component
public class FirstWonBadgeProcessor implements BadgeProcessor {
    @Override
    public Optional<BadgeType> process(int currentScore, List<ScoreCard> scoreCardList, ChallengeSolvedDTO challenge) {
        if (scoreCardList.size() == 1) {
            return Optional.of(BadgeType.FIRST_WON);
        }
        return Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return BadgeType.FIRST_WON;
    }
}
