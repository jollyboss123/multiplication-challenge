package org.jolly.multiplication.gamification.game;

import org.jolly.multiplication.gamification.challenge.ChallengeSolvedEvent;
import org.jolly.multiplication.gamification.game.badgeprocessors.BadgeProcessor;
import org.jolly.multiplication.gamification.game.domain.BadgeCard;
import org.jolly.multiplication.gamification.game.domain.BadgeType;
import org.jolly.multiplication.gamification.game.domain.ScoreCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.jolly.multiplication.gamification.game.GameService.GameResult;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;

/**
 * @author jolly
 */
@ExtendWith(MockitoExtension.class)
class GameServiceTest {
    private GameService gameService;
    @Mock
    private ScoreRepository scoreRepository;
    @Mock
    private BadgeRepository badgeRepository;
    @Mock
    private BadgeProcessor badgeProcessor;

    @BeforeEach
    void setUp() {
        gameService = new GameServiceImpl(
                scoreRepository,
                badgeRepository,
                List.of(badgeProcessor)
        );
    }

    @Test
    void correctAttemptTest() {
        // given
        final long userId = 1L;
        final long attemptId = 1L;
        final ChallengeSolvedEvent challenge = new ChallengeSolvedEvent(attemptId, true, 58, 92, userId, "john_doe");
        final ScoreCard scoreCard = new ScoreCard(userId, attemptId);
        given(scoreRepository.findTotalByUser(userId))
                .willReturn(Optional.of(10));
        given(scoreRepository.findByUserId(userId))
                .willReturn(List.of(scoreCard));
        given(badgeRepository.findAllByUserId(userId))
                .willReturn(List.of(new BadgeCard(1L, BadgeType.FIRST_WON)));
        given(badgeProcessor.badgeType())
                .willReturn(BadgeType.LUCKY_NUMBER);
        given(badgeProcessor.process(10, List.of(scoreCard), challenge))
                .willReturn(Optional.of(BadgeType.LUCKY_NUMBER));
        // when
        final GameResult res = gameService.newAttemptForUser(challenge);
        // then
        then(res).isEqualTo(new GameResult(10, List.of(BadgeType.LUCKY_NUMBER)));
        verify(scoreRepository).save(scoreCard);
        verify(badgeRepository).saveAll(List.of(new BadgeCard(userId, BadgeType.LUCKY_NUMBER)));
    }

    @Test
    void wrongAttemptTest() {
        // given
        final ChallengeSolvedEvent challenge = new ChallengeSolvedEvent(1L, false, 58, 92, 1L, "john_doe");
        // when
        final GameResult res = gameService.newAttemptForUser(challenge);
        // then
        then(res).isEqualTo(new GameResult(0, Collections.emptyList()));
    }
}
