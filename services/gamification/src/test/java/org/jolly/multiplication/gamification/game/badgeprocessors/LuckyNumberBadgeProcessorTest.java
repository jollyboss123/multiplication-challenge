package org.jolly.multiplication.gamification.game.badgeprocessors;

import org.jolly.multiplication.gamification.challenge.ChallengeSolvedDTO;
import org.jolly.multiplication.gamification.game.domain.BadgeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author jolly
 */
class LuckyNumberBadgeProcessorTest {
    private LuckyNumberBadgeProcessor luckyNumberBadgeProcessor;

    @BeforeEach
    void setUp() {
        luckyNumberBadgeProcessor = new LuckyNumberBadgeProcessor();
    }

    @ParameterizedTest
    @MethodSource("factorLucky")
    void luckyNumberHit(int factorA, int factorB) {
        final ChallengeSolvedDTO challenge = new ChallengeSolvedDTO(1L, true, factorA, factorB, 1L, "john_doe");
        final Optional<BadgeType> res = luckyNumberBadgeProcessor.process(0, Collections.emptyList(), challenge);
        assertThat(res).contains(BadgeType.LUCKY_NUMBER);
    }

    @ParameterizedTest
    @MethodSource("factorMiss")
    void luckyNumberMiss(int factorA, int factorB) {
        final ChallengeSolvedDTO challenge = new ChallengeSolvedDTO(1L, true, factorA, factorB, 1L, "john_doe");
        final Optional<BadgeType> res = luckyNumberBadgeProcessor.process(0, Collections.emptyList(), challenge);
        assertThat(res).isEmpty();
    }

    private static Stream<Arguments> factorLucky() {
        return Stream.of(
                Arguments.of(42, 0),
                Arguments.of(0, 42)
        );
    }

    private static Stream<Arguments> factorMiss() {
        return Stream.of(
                Arguments.of(41, 0),
                Arguments.of(0, 43)
        );
    }
}
