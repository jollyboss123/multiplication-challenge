package org.jolly.multiplication.challenge.challenge;

import java.util.List;

/**
 * @author jolly
 */
public interface ChallengeService {
    /**
     * Verifies if an attempt coming from the presentation layer is correct or not.
     *
     * @return the resulting {@link ChallengeAttempt}
     */
    ChallengeAttempt verifyAttempt(final ChallengeAttemptDTO resultAttempt);

    /**
     * Gets the statistics for a given user.
     *
     * @param alias the user's alias
     * @return a list of the last 10 {@link ChallengeAttempt} objects created by the user
     */
    List<ChallengeAttempt> getStatsForUser(final String alias);
}
