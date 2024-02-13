package org.jolly.multiplication.gamification.game;

import org.jolly.multiplication.gamification.game.domain.BadgeCard;
import org.jolly.multiplication.gamification.game.domain.BadgeType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jolly
 */
public interface BadgeRepository extends CrudRepository<BadgeCard, Long> {
    /**
     * Retrieves all unique {@link BadgeType}s for a given user.
     *
     * @param userId the id of the user for lookup
     * @return set of {@link BadgeType}s, ordered by most recent first
     */
    @Transactional(readOnly = true)
    @Query("""
            select b
            from BadgeCard b
            where b.userId = :userId
            order by b.badgeType asc, b.timestamp desc
            """)
    Iterable<BadgeCard> findAllByUserId(@Param("userId") Long userId);
}
