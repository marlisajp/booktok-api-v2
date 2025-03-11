package com.marlisajp.booktok_api_v2.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveAndFindUser() {
        User user = new User();
        user.setClerkId("user_123");
        user.setEmailAddress("user@example.com");
        user.setFullName("John Doe");

        User savedUser = userRepository.save(user);
        assertThat(savedUser.getId()).isNotNull();

        User foundUser = userRepository.findByClerkId("user_123").orElse(null);
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getFullName()).isEqualTo("John Doe");
    }
}
