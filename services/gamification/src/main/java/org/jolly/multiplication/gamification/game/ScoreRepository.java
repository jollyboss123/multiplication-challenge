package org.jolly.multiplication.gamification.game;

import org.jolly.multiplication.gamification.game.domain.LeaderBoardRow;
import org.jolly.multiplication.gamification.game.domain.ScoreCard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author jolly
 */
public interface ScoreRepository extends CrudRepository<ScoreCard, Long> {
    /**
     * Gets the total score for a given user: the sum of the {@link ScoreCard#getScore()}.
     *
     * @param userId the id of the user
     * @return the total score of the user, empty if the user does not exist
     */
    @Transactional(readOnly = true)
    @Query("""
            select sum(s.score)
            from ScoreCard s
            where s.userId = :userId
            """)
    Optional<Integer> findTotalByUser(@Param("userId") Long userId);

    /**
     * Retrieves a list of {@link LeaderBoardRow}s representing the leader board of users
     * and their total score.
     *
     * @return the leader board, sorted by highest score first
     */
    @Transactional(readOnly = true)
    @Query("""
            select new org.jolly.multiplication.gamification.game.domain.LeaderBoardRow(s.userId, sum(s.score))
            from ScoreCard s
            group by s.userId
            order by sum(s.score) desc
            """)
    Iterable<LeaderBoardRow> findTop10();

    /**
     * Retrieves all the {@link ScoreCard}s for a given user, identified by his user id.
     *
     * @param userId the id of the user
     * @return an iterable containing all the {@link ScoreCard}s for the given user, sorted by most recent
     */
    @Transactional(readOnly = true)
    @Query("""
            select s
            from ScoreCard s
            where s.userId = :userId
            order by s.score asc, s.timestamp desc
            """)
    Iterable<ScoreCard> findByUserId(@Param("userId") Long userId);
}
