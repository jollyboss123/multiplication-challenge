package org.jolly.multiplication.backend.challenge;

/**
 * @author jolly
 */
public interface ChallengeService {
    /**
     * Verifies if an attempt coming from the presentation layer is correct or not
     */
    ChallengeAttempt verifyAttempt(final ChallengeAttemptDTO resultAttempt);
}
