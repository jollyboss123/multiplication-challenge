package org.jolly.multiplication.gamification.game.badgeprocessors;

import org.jolly.multiplication.gamification.game.domain.BadgeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author jolly
 */
class GoldBadgeProcessorTest {
    private GoldBadgeProcessor goldBadgeProcessor;

    @BeforeEach
    void setUp() {
        goldBadgeProcessor = new GoldBadgeProcessor();
    }

    @Test
    void scoreOverThreshold() {
        final int score = 401;
        final Optional<BadgeType> res = goldBadgeProcessor.process(score, Collections.emptyList(), null);
        assertThat(res).contains(BadgeType.GOLD);
    }

    @Test
    void scoreUnderThreshold() {
        final int score = 399;
        final Optional<BadgeType> res = goldBadgeProcessor.process(score, Collections.emptyList(), null);
        assertThat(res).isEmpty();
    }
}
