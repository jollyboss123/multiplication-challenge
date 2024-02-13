package org.jolly.multiplication.gamification.game;

import lombok.RequiredArgsConstructor;
import org.jolly.multiplication.gamification.game.domain.BadgeCard;
import org.jolly.multiplication.gamification.game.domain.LeaderBoardRow;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jolly
 */
@Service
@RequiredArgsConstructor
public class LeaderBoardServiceImpl implements LeaderBoardService {
    private final ScoreRepository scoreRepository;
    private final BadgeRepository badgeRepository;

    @Override
    public List<LeaderBoardRow> getCurrentLeaderBoard() {
        final List<LeaderBoardRow> scores = (List<LeaderBoardRow>) scoreRepository.findTop10();

        List<LeaderBoardRow> aggregated = new ArrayList<>();
        for (var s : scores) {
            final List<String> badges = ((List<BadgeCard>) badgeRepository.findAllByUserId(s.getUserId()))
                    .stream()
                    .map(b -> b.getBadgeType().getDescription())
                    .toList();
            aggregated.add(s.withBadges(badges));
        }
        return aggregated;
    }
}
