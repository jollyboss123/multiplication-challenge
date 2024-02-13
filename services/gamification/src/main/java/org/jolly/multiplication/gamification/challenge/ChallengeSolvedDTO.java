package org.jolly.multiplication.gamification.challenge;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

/**
 * @author jolly
 */
@Value
public class ChallengeSolvedDTO {
    long attemptId;
    boolean correct;
    @Min(1) @Max(99)
    int factorA;
    @Min(1) @Max(99)
    int factorB;
    long userId;
    @NotBlank
    String userAlias;
}
