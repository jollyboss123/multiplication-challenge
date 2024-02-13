package org.jolly.multiplication.gamification.game;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jolly.multiplication.gamification.challenge.ChallengeSolvedDTO;
import org.jolly.multiplication.gamification.game.badgeprocessors.BadgeProcessor;
import org.jolly.multiplication.gamification.game.domain.BadgeCard;
import org.jolly.multiplication.gamification.game.domain.BadgeType;
import org.jolly.multiplication.gamification.game.domain.ScoreCard;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author jolly
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl implements GameService {
    private final ScoreRepository scoreRepository;
    private final BadgeRepository badgeRepository;
    private final List<BadgeProcessor> badgeProcessors;

    @Override
    public GameResult newAttemptForUser(final ChallengeSolvedDTO challenge) {
        if (challenge.isCorrect()) {
            final ScoreCard scoreCard = new ScoreCard(challenge.getUserId(), challenge.getAttemptId());
            scoreRepository.save(scoreCard);
            log.info("user #{} scored: {} for attempt #{}", challenge.getUserId(), scoreCard.getScore(), challenge.getAttemptId());
            final List<BadgeCard> badgeCards = processForBadges(challenge);
            return new GameResult(scoreCard.getScore(), badgeCards.stream()
                    .map(BadgeCard::getBadgeType)
                    .toList());
        } else {
            log.info("attempt #{} is not correct, user #{} does not get score", challenge.getAttemptId(), challenge.getUserId());
            return new GameResult(0, Collections.emptyList());
        }
    }

    private List<BadgeCard> processForBadges(final ChallengeSolvedDTO challenge) {
        final Integer totalScore = scoreRepository.findTotalByUser(challenge.getUserId())
                .orElse(null);
        if (totalScore == null) {
            return Collections.emptyList();
        }

        final List<ScoreCard> scoreCardList = (List<ScoreCard>) scoreRepository.findByUserId(challenge.getUserId());
        final Set<BadgeType> existingBadges = ((List<BadgeCard>) badgeRepository.findAllByUserId(challenge.getUserId())).stream()
                .map(BadgeCard::getBadgeType)
                .collect(Collectors.toSet());

        final List<BadgeCard> newBadgeCards = new ArrayList<>();
        for (var bp : badgeProcessors) {
            if (!existingBadges.contains(bp.badgeType())) {
                bp.process(totalScore, scoreCardList, challenge)
                        .ifPresent(badgeType -> newBadgeCards.add(new BadgeCard(challenge.getUserId(), badgeType)));
            }
        }
        badgeRepository.saveAll(newBadgeCards);
        return newBadgeCards;
    }
}
