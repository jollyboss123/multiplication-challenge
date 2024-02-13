package org.jolly.multiplication.gamification.game.badgeprocessors;

import org.assertj.core.api.Assertions;
import org.jolly.multiplication.gamification.game.domain.BadgeType;
import org.jolly.multiplication.gamification.game.domain.ScoreCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author jolly
 */
class FirstWonBadgeProcessorTest {
    private FirstWonBadgeProcessor firstWonBadgeProcessor;

    @BeforeEach
    void setUp() {
        firstWonBadgeProcessor = new FirstWonBadgeProcessor();
    }

    @Test
    void scoreCardListAtThreshold() {
        final ScoreCard scoreCard = new ScoreCard(1L, 1L);
        final List<ScoreCard> scoreCardList = List.of(scoreCard);
        final Optional<BadgeType> res = firstWonBadgeProcessor.process(0, scoreCardList, null);
        assertThat(res).contains(BadgeType.FIRST_WON);
    }

    @Test
    void scoreCardListOverThreshold() {
        final ScoreCard scoreCard = new ScoreCard(1L, 1L);
        final List<ScoreCard> scoreCardList = List.of(
                scoreCard,
                scoreCard,
                scoreCard
        );
        final Optional<BadgeType> res = firstWonBadgeProcessor.process(0, scoreCardList, null);
        assertThat(res).isEmpty();
    }

    @Test
    void scoreCardListUnderThreshold() {
        final Optional<BadgeType> res = firstWonBadgeProcessor.process(0, Collections.emptyList(), null);
        assertThat(res).isEmpty();
    }
}
