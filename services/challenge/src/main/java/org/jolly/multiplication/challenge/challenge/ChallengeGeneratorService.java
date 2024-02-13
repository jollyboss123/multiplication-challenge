package org.jolly.multiplication.challenge.challenge;

/**
 * @author jolly
 */
public interface ChallengeGeneratorService {
    /**
     * @return a randomly generated challenge with factors between 11 and 99
     */
    Challenge randomChallenge();
}
