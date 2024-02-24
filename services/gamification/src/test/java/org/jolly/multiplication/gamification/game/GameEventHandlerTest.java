package org.jolly.multiplication.gamification.game;

import org.jolly.multiplication.gamification.challenge.ChallengeSolvedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;

/**
 * @author jolly
 */
@ExtendWith(MockitoExtension.class)
class GameEventHandlerTest {
    private GameEventHandler gameEventHandler;
    @Mock
    private GameService gameService;

    @BeforeEach
    void setUp() {
        gameEventHandler = new GameEventHandler(gameService);
    }

    @Test
    void handleMultiplicationSolved() {
        // given
        final ChallengeSolvedEvent event = new ChallengeSolvedEvent(
                1L,
                true,
                30,
                40,
                10L,
                "john"
        );
        // when
        assertDoesNotThrow(() -> gameEventHandler.handleMultiplicationSolved(event));
        // then
        verify(gameService).newAttemptForUser(event);
    }

    @Test
    void handleGameServiceFailProcessEvent() {
        // given
        final ChallengeSolvedEvent event = new ChallengeSolvedEvent(
                1L,
                true,
                30,
                40,
                10L,
                "john"
        );
        given(gameService.newAttemptForUser(event))
                .willThrow();
        // when
        // then
        assertThrows(AmqpRejectAndDontRequeueException.class, () -> gameEventHandler.handleMultiplicationSolved(event));
    }
}
