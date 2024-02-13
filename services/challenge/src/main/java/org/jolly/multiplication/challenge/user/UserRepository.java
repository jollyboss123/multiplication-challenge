package org.jolly.multiplication.challenge.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author jolly
 */
public interface UserRepository extends CrudRepository<User, Long> {
    @Query("""
            select u
            from User u
            where u.alias = :alias
            """)
    Optional<User> findByAlias(@Param("alias") String alias);

    @Query("""
            select u
            from User u
            where u.id in (:ids)
            """)
    Iterable<User> findAllByIdIn(@Param("ids") List<Long> ids);
}
