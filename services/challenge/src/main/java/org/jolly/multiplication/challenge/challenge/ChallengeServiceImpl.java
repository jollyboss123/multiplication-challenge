package org.jolly.multiplication.challenge.challenge;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jolly.multiplication.challenge.user.User;
import org.jolly.multiplication.challenge.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jolly
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ChallengeServiceImpl implements ChallengeService {
    private final UserRepository userRepository;
    private final ChallengeAttemptRepository attemptRepository;
    private final ChallengeEventPub challengeEventPub;

    @Override
    public ChallengeAttempt verifyAttempt(ChallengeAttemptDTO resultAttempt) {
        // check if user exists
        final User user = userRepository.findByAlias(resultAttempt.getUserAlias())
                .orElseGet(() -> {
                    log.info("creating new user with alias: [{}]", resultAttempt.getUserAlias());
                    return userRepository.save(new User(resultAttempt.getUserAlias()));
                });
        final boolean isCorrect = resultAttempt.getGuess() == resultAttempt.getFactorA() * resultAttempt.getFactorB();
        final ChallengeAttempt checkedAttempt = new ChallengeAttempt(
                null,
                user,
                resultAttempt.getFactorA(),
                resultAttempt.getFactorB(),
                resultAttempt.getGuess(),
                isCorrect
        );
        final ChallengeAttempt storedAttempt = attemptRepository.save(checkedAttempt);
        challengeEventPub.challengeSolved(storedAttempt);
        return storedAttempt;
    }

    @Override
    public List<ChallengeAttempt> getStatsForUser(String alias) {
        return (List<ChallengeAttempt>) attemptRepository.findTop10ByUserAlias(alias);
    }
}
