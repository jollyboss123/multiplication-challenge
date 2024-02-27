package org.jolly.multiplication.challenge.user;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.BDDAssertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * @author jolly
 */
@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(UserController.class)
@TestPropertySource("classpath:/application.properties")
class UserControllerTest {
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester<List<User>> jsonResultUsers;

    @Test
    void getUsers() throws Exception {
        // given
        final User john = new User(1L, "john");
        final User mary = new User(2L, "mary");
        final List<User> expected = List.of(john, mary);
        final List<Long> ids = List.of(1L, 2L);
        given(userRepository.findAllByIdIn(ids))
                .willReturn(expected);
        // when
        final MockHttpServletResponse response = mvc.perform(
                get("/users/%s".formatted(ids.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(","))))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString())
                .isEqualTo(jsonResultUsers.write(expected).getJson());
    }
}
