package org.jolly.multiplication.challenge.challenge;

import lombok.Value;

/**
 * @author jolly
 */
@Value
public class ChallengeSolvedEvent {
    long attemptId;
    boolean correct;
    int factorA;
    int factorB;
    long userId;
    String userAlias;
}
