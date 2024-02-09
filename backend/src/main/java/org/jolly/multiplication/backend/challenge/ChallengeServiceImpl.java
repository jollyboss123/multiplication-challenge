package org.jolly.multiplication.backend.challenge;

import org.jolly.multiplication.backend.user.User;
import org.springframework.stereotype.Service;

/**
 * @author jolly
 */
@Service
public class ChallengeServiceImpl implements ChallengeService {
    @Override
    public ChallengeAttempt verifyAttempt(ChallengeAttemptDTO resultAttempt) {
        final boolean isCorrect = resultAttempt.getGuess() == resultAttempt.getFactorA() * resultAttempt.getFactorB();
        final User user = new User(null, resultAttempt.getUserAlias());
        return new ChallengeAttempt(
                null,
                user.getId(),
                resultAttempt.getFactorA(),
                resultAttempt.getFactorB(),
                resultAttempt.getGuess(),
                isCorrect
        );
    }
}
