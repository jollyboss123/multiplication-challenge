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
class SilverBadgeProcessorTest {
    private SilverBadgeProcessor silverBadgeProcessor;

    @BeforeEach
    void setUp() {
        silverBadgeProcessor = new SilverBadgeProcessor();
    }

    @Test
    void scoreOverThreshold() {
        final int score = 151;
        final Optional<BadgeType> res = silverBadgeProcessor.process(score, Collections.emptyList(), null);
        assertThat(res).contains(BadgeType.SILVER);
    }

    @Test
    void scoreUnderThreshold() {
        final int score = 149;
        final Optional<BadgeType> res = silverBadgeProcessor.process(score, Collections.emptyList(), null);
        assertThat(res).isEmpty();
    }
}
