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
class BronzeBadgeProcessorTest {
    private BronzeBadgeProcessor bronzeBadgeProcessor;

    @BeforeEach
    void setUp() {
        bronzeBadgeProcessor = new BronzeBadgeProcessor();
    }

    @Test
    void scoreOverThreshold() {
        final int score = 60;
        Optional<BadgeType> res = bronzeBadgeProcessor.process(score, Collections.emptyList(), null);
        assertThat(res).contains(BadgeType.BRONZE);
    }

    @Test
    void scoreUnderThreshold() {
        final int score = 40;
        Optional<BadgeType> res = bronzeBadgeProcessor.process(score, Collections.emptyList(), null);
        assertThat(res).isEmpty();
    }
}
