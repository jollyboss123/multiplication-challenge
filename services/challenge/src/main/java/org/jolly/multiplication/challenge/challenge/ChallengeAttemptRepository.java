package org.jolly.multiplication.challenge.challenge;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author jolly
 */
public interface ChallengeAttemptRepository extends CrudRepository<ChallengeAttempt, Long> {
    @Query("""
            select c
            from ChallengeAttempt c
            where c.user.alias = :alias
            order by c.id desc
            limit 10
            """)
    Iterable<ChallengeAttempt> findTop10ByUserAlias(@Param("alias") String alias);
}
