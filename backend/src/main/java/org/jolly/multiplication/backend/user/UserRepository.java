package org.jolly.multiplication.backend.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

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
}
