package org.jolly.multiplication.backend.challenge;

import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author jolly
 */
@Service
public class ChallengeGeneratorServiceImpl implements ChallengeGeneratorService {
    private final Random random;

    private static final int MIN_FACTOR = 11;
    private static final int MAX_FACTOR = 100;

    ChallengeGeneratorServiceImpl() {
        this.random = new Random();
    }

    ChallengeGeneratorServiceImpl(final Random random) {
        this.random = random;
    }


    @Override
    public Challenge randomChallenge() {
        return new Challenge(next(), next());
    }

    private int next() {
        return random.nextInt(MAX_FACTOR - MIN_FACTOR) + MIN_FACTOR;
    }
}
