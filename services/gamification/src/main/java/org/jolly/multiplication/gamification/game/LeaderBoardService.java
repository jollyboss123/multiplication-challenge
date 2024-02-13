package org.jolly.multiplication.gamification.game;

import org.jolly.multiplication.gamification.game.domain.LeaderBoardRow;

import java.util.List;

/**
 * @author jolly
 */
public interface LeaderBoardService {
    /**
     * @return the current leader board ranked from highest to lowest score
     */
    List<LeaderBoardRow> getCurrentLeaderBoard();
}
