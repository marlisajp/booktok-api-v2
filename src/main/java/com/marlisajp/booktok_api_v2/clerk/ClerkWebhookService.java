package com.marlisajp.booktok_api_v2.clerk;

import com.fasterxml.jackson.databind.JsonNode;
import com.marlisajp.booktok_api_v2.bookcase.Bookcase;
import com.marlisajp.booktok_api_v2.exception.UserCreationException;
import com.marlisajp.booktok_api_v2.user.User;
import com.marlisajp.booktok_api_v2.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClerkWebhookService {
    private static final Logger logger = LoggerFactory.getLogger(ClerkWebhookService.class);
    private final UserRepository userRepository;
    private final ClerkWebhookUtil clerkWebhookUtil;

    public ClerkWebhookService(UserRepository userRepository, ClerkWebhookUtil clerkWebhookUtil){
        this.userRepository = userRepository;
        this.clerkWebhookUtil = clerkWebhookUtil;
    }
    public ClerkWebhookResponse createUserInDatabase(JsonNode userData) {
        ClerkUserData clerkUser = clerkWebhookUtil.extractUserData(userData);

        boolean existsByClerkId = userRepository.findByClerkId(clerkUser.getClerkId()).isPresent();
        boolean existsByEmail = userRepository.findByEmailAddress(clerkUser.getEmailAddress()).isPresent();

        if (existsByClerkId || existsByEmail) {
            logger.info("User with clerkId {} or email {} already exists; skipping database instance creation",
                    clerkUser.getClerkId(), clerkUser.getEmailAddress());
            throw new UserCreationException(
                    HttpStatus.BAD_REQUEST,
                    HttpStatus.BAD_REQUEST.value(),
                    ClerkWebhookResponse.USER_ALREADY_EXISTS.getMessage());
        }

        User newUser = User.builder()
                .clerkId(clerkUser.getClerkId())
                .emailAddress(clerkUser.getEmailAddress())
                .username(clerkUser.getUsername())
                .bookcase(new Bookcase())
                .build();

        newUser.getBookcase().setUser(newUser);
        userRepository.save(newUser);
        logger.info("Created new user: {}", newUser.getUsername());
        return ClerkWebhookResponse.USER_CREATED_SUCCESS;
    }

    public ClerkWebhookResponse deleteUserFromDatabase(JsonNode userData) {
        ClerkUserData clerkUser = clerkWebhookUtil.extractUserData(userData);

        Optional<User> optionalUser = userRepository.findByClerkId(clerkUser.getClerkId());
        if (optionalUser.isEmpty()) {
            logger.info("User with clerkId {} not found in database...", clerkUser.getClerkId());
            return ClerkWebhookResponse.USER_DOESNT_EXIST;
        }

        User foundUser = optionalUser.get();
        userRepository.delete(foundUser);
        logger.info("Deleted user {} from Booktok Database...", clerkUser);
        return ClerkWebhookResponse.USER_DELETED_SUCCESS;
    }


    public ClerkWebhookResponse updateUserInDatabase(JsonNode userData) {
        ClerkUserData clerkUser = clerkWebhookUtil.extractUserData(userData);

        Optional<User> optionalUser = userRepository.findByClerkId(clerkUser.getClerkId());
        if (optionalUser.isEmpty()) {
            logger.info("User with Clerk Id {} does not exist in database.", clerkUser.getClerkId());
            return ClerkWebhookResponse.USER_DOESNT_EXIST;
        }
        User foundUser = optionalUser.get();
        foundUser.setUsername(clerkUser.getUsername());
        foundUser.setEmailAddress(clerkUser.getEmailAddress());
        userRepository.save(foundUser);
        logger.info("Updated user: {}", foundUser);
        return ClerkWebhookResponse.USER_UPDATED_SUCCESS;
    }


}
