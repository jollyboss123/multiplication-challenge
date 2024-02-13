package org.jolly.multiplication.gamification.game;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jolly.multiplication.gamification.challenge.ChallengeSolvedDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author jolly
 */
@RestController
@RequestMapping("/attempts")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    void postResult(@RequestBody @Valid ChallengeSolvedDTO dto) {
        gameService.newAttemptForUser(dto);
    }
}
