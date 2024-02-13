package org.jolly.multiplication.challenge.servicesclient;

import lombok.extern.slf4j.Slf4j;
import org.jolly.multiplication.challenge.challenge.ChallengeAttempt;
import org.jolly.multiplication.challenge.challenge.ChallengeSolvedDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author jolly
 */
@Service
@Slf4j
public class GamificationServiceClient {
    private final RestTemplate restTemplate;
    private final String gamificationHostUrl;

    public GamificationServiceClient(final RestTemplateBuilder builder,
                                     @Value("${service.gamification.host}") final String gamificationHostUrl) {
        this.restTemplate = builder.build();
        this.gamificationHostUrl = gamificationHostUrl;
    }

    public boolean sendAttempt(final ChallengeAttempt attempt) {
        try {
            ChallengeSolvedDTO dto = new ChallengeSolvedDTO(
                    attempt.getId(),
                    attempt.isCorrect(),
                    attempt.getFactorA(),
                    attempt.getFactorB(),
                    attempt.getUser().getId(),
                    attempt.getUser().getAlias()
            );

            ResponseEntity<String> r = restTemplate.postForEntity(
                    "%s/attempts".formatted(gamificationHostUrl),
                    dto,
                    String.class
            );
            log.info("gamification service response: {}", r.getStatusCode());
            return r.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            log.error("there was a problem sending the attempt", e);
            return false;
        }
    }
}
