package com.marlisajp.booktok_api_v2.clerk;

import com.fasterxml.jackson.databind.JsonNode;
import com.marlisajp.booktok_api_v2.bookcase.Bookcase;
import com.marlisajp.booktok_api_v2.exception.GenericException;
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
        logger.info("Created new user: {}", newUser.getClerkId());
        return ClerkWebhookResponse.USER_CREATED_SUCCESS;
    }

    public ClerkWebhookResponse deleteUserFromDatabase(JsonNode userData) {
        String clerkId = userData.get("id").asText();
        User user = userRepository.findByClerkId(clerkId)
                .orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), "User does not exist"));

        userRepository.delete(user);
        logger.info("Deleted {} from booktokdb...", clerkId);
        return ClerkWebhookResponse.USER_DELETED_SUCCESS;
    }


    public ClerkWebhookResponse updateUserInDatabase(JsonNode userData) {
        ClerkUserData clerkUser = clerkWebhookUtil.extractUserData(userData);
        User user = userRepository.findByClerkId(clerkUser.getClerkId())
                .orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), "User does not exist"));

        user.setUsername(clerkUser.getUsername());
        user.setEmailAddress(clerkUser.getEmailAddress());
        userRepository.save(user);
        logger.info("Updated user: {}", user.getClerkId());
        return ClerkWebhookResponse.USER_UPDATED_SUCCESS;
    }


}
