package org.jolly.multiplication.gamification.game;

import lombok.Value;
import org.jolly.multiplication.gamification.challenge.ChallengeSolvedDTO;
import org.jolly.multiplication.gamification.game.domain.BadgeType;

import java.util.List;

/**
 * @author jolly
 */
public interface GameService {
    /**
     * Process a new attempt from a given user.
     *
     * @param challenge the challenge data with user details, factors, etc.
     * @return a {@link GameResult} containing the new score and badge cards obtained
     */
    GameResult newAttemptForUser(final ChallengeSolvedDTO challenge);

    @Value
    class GameResult {
        int score;
        List<BadgeType> badges;
    }
}
