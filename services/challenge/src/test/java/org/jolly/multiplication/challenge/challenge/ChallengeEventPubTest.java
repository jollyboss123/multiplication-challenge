package org.jolly.multiplication.challenge.challenge;

import org.jolly.multiplication.challenge.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;

/**
 * @author jolly
 */
@ExtendWith(MockitoExtension.class)
class ChallengeEventPubTest {
    private ChallengeEventPub challengeEventPub;
    @Mock
    private AmqpTemplate amqpTemplate;

    @BeforeEach
    void setUp() {
        challengeEventPub = new ChallengeEventPub(
                amqpTemplate,
                "test.topic"
        );
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void sendsAttempt(boolean correct) {
        // given
        final ChallengeAttempt attempt = new ChallengeAttempt(
                1L,
                new User(10L, "john"),
                30,
                40,
                correct ? 1200 : 1300,
                correct
        );
        // when
        assertDoesNotThrow(() -> challengeEventPub.challengeSolved(attempt));
        // then
        var exchangeCaptor = ArgumentCaptor.forClass(String.class);
        var routingKeyCaptor = ArgumentCaptor.forClass(String.class);
        var eventCaptor = ArgumentCaptor.forClass(ChallengeSolvedEvent.class);

        verify(amqpTemplate).convertAndSend(
                exchangeCaptor.capture(),
                routingKeyCaptor.capture(),
                eventCaptor.capture()
        );
        then(exchangeCaptor.getValue()).isEqualTo("test.topic");
        then(routingKeyCaptor.getValue()).isEqualTo("attempts.%s".formatted(correct ? "correct" : "wrong"));
        then(eventCaptor.getValue()).isEqualTo(new ChallengeSolvedEvent(1L, correct, 30, 40, 10L , "john"));
    }
}
