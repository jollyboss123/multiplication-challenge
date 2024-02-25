package org.jolly.multiplication.gamification.game;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jolly.multiplication.gamification.game.domain.LeaderBoardRow;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jolly
 */
@RestController
@RequestMapping("/leaders")
@RequiredArgsConstructor
@Slf4j
public class LeaderBoardController {
    private final LeaderBoardService leaderBoardService;

    @GetMapping
    List<LeaderBoardRow> getLeaderBoard() {
        log.info("retrieving leaderboard");
        return leaderBoardService.getCurrentLeaderBoard();
    }
}
