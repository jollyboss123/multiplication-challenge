package org.jolly.multiplication.gamification.game;

import org.jolly.multiplication.gamification.game.domain.BadgeCard;
import org.jolly.multiplication.gamification.game.domain.BadgeType;
import org.jolly.multiplication.gamification.game.domain.LeaderBoardRow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

/**
 * @author jolly
 */
@ExtendWith(MockitoExtension.class)
class LeaderBoardServiceTest {
    private LeaderBoardService leaderBoardService;
    @Mock
    private ScoreRepository scoreRepository;
    @Mock
    private BadgeRepository badgeRepository;

    @BeforeEach
    void setUp() {
        leaderBoardService = new LeaderBoardServiceImpl(scoreRepository, badgeRepository);
    }

    @Test
    void getCurrentLeaderBoardTest() {
        // given
        final LeaderBoardRow scoreRow = new LeaderBoardRow(1L, 300L, Collections.emptyList());
        given(scoreRepository.findTop10())
                .willReturn(List.of(scoreRow));
        given(badgeRepository.findAllByUserId(1L))
                .willReturn(List.of(new BadgeCard(1L, BadgeType.LUCKY_NUMBER)));
        // when
        final List<LeaderBoardRow> res = leaderBoardService.getCurrentLeaderBoard();
        // then
        final List<LeaderBoardRow> expected = List.of(
                new LeaderBoardRow(1L, 300L, List.of(BadgeType.LUCKY_NUMBER.getDescription()))
        );
        then(res).isEqualTo(expected);
    }
}
