package org.jolly.multiplication.challenge.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jolly
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserRepository userRepository;

    @GetMapping("/{idList}")
    List<User> getUsers(@PathVariable final List<Long> idList) {
        log.info("resolving aliases for users: {}", idList);
        return (List<User>) userRepository.findAllByIdIn(idList);
    }
}
