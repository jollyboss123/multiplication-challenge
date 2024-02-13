package org.jolly.multiplication.challenge.challenge;

import lombok.Value;

/**
 * @author jolly
 */
@Value
public class ChallengeSolvedDTO {
    long attemptId;
    boolean correct;
    int factorA;
    int factorB;
    long userId;
    String userAlias;
}
