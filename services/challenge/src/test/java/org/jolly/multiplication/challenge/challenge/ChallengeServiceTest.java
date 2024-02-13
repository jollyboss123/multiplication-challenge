package org.jolly.multiplication.challenge.challenge;

import org.jolly.multiplication.challenge.servicesclient.GamificationServiceClient;
import org.jolly.multiplication.challenge.user.User;
import org.jolly.multiplication.challenge.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * @author jolly
 */
@ExtendWith(MockitoExtension.class)
class ChallengeServiceTest {
    private ChallengeService challengeService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ChallengeAttemptRepository attemptRepository;
    @Mock
    private GamificationServiceClient gameClient;

    @BeforeEach
    void setUp() {
        challengeService = new ChallengeServiceImpl(
                userRepository,
                attemptRepository,
                gameClient
        );
    }

    @Test
    void checkCorrectAttemptTest() {
        // given
        final ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 60, "john_doe", 3000);
        given(attemptRepository.save(any()))
                .will(returnsFirstArg());
        // when
        final ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);
        // then
        then(resultAttempt.isCorrect()).isTrue();
        verify(userRepository).save(argThat(user -> "john_doe".equals(user.getAlias())));
        verify(attemptRepository).save(resultAttempt);
        verify(gameClient).sendAttempt(resultAttempt);
    }

    @Test
    void checkWrongAttemptTest() {
        // given
        final ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 60, "john_doe", 5000);
        given(attemptRepository.save(any()))
                .will(returnsFirstArg());
        // when
        final ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);
        // then
        then(resultAttempt.isCorrect()).isFalse();
        verify(gameClient).sendAttempt(resultAttempt);
    }

    @Test
    void checkExistingUserTest() {
        // given
        final User existingUser = new User(1L, "john_doe");
        given(attemptRepository.save(any()))
                .will(returnsFirstArg());
        given(userRepository.findByAlias(existingUser.getAlias()))
                .willReturn(Optional.of(existingUser));
        final ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 60, "john_doe", 5000);
        // when
        final ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);
        // then
        then(resultAttempt.isCorrect()).isFalse();
        then(resultAttempt.getUser()).isEqualTo(existingUser);
        verify(userRepository, never()).save(any());
        verify(attemptRepository).save(resultAttempt);
        verify(gameClient).sendAttempt(resultAttempt);
    }

    @Test
    void getLastAttempts() {
        // given
        final User user = new User("john_doe");
        final ChallengeAttempt attempt1 = new ChallengeAttempt(1L, user, 60, 50, 5000, false);
        final ChallengeAttempt attempt2 = new ChallengeAttempt(2L, user, 50, 60, 3000, true);
        final List<ChallengeAttempt> lastAttempts = List.of(attempt1, attempt2);
        given(attemptRepository.findTop10ByUserAlias(user.getAlias()))
                .willReturn(lastAttempts);
        // when
        final List<ChallengeAttempt> resultAttempts = challengeService.getStatsForUser(user.getAlias());
        // then
        then(resultAttempts).isEqualTo(lastAttempts);
    }
}
