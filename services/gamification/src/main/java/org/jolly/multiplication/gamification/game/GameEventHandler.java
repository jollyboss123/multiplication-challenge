package org.jolly.multiplication.gamification.game;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jolly.multiplication.gamification.challenge.ChallengeSolvedEvent;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author jolly
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class GameEventHandler {
    private final GameService gameService;

    @RabbitListener(queues = "${amqp.queue.gamification}")
    void handleMultiplicationSolved(final ChallengeSolvedEvent event) {
        log.info("challenge solved event received: {}", event.getAttemptId());

        try {
            gameService.newAttemptForUser(event);
        } catch (final Exception e) {
            log.error("error when trying to process ChallengeSolvedEvent", e);
            // avoids the event to be re-queued and reprocessed
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}
