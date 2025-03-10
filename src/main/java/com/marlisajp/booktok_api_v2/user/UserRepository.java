package com.marlisajp.booktok_api_v2.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByClerkId(String clerkId);
    Optional<User> findByEmailAddress(String emailAddress);
}
