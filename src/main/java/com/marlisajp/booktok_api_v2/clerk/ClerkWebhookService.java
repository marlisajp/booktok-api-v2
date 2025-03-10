package com.marlisajp.booktok_api_v2.clerk;

import com.fasterxml.jackson.databind.JsonNode;
import com.marlisajp.booktok_api_v2.bookcase.Bookcase;
import com.marlisajp.booktok_api_v2.exception.UserCreationException;
import com.marlisajp.booktok_api_v2.user.User;
import com.marlisajp.booktok_api_v2.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public ClerkWebhookResponse createUserInDatabase(JsonNode userData) throws UserCreationException{
        ClerkUserData clerkUser = clerkWebhookUtil.extractUserData(userData);

        try {
            boolean existsByClerkId = userRepository.findByClerkId(clerkUser.getClerkId()).isPresent();
            boolean existsByEmail = userRepository.findByEmailAddress(clerkUser.getEmailAddress()).isPresent();

            if(existsByClerkId || existsByEmail){
                logger.info("User with clerkId {} or email {} already exists; skipping database instance creation",
                        clerkUser.getClerkId(),
                        clerkUser.getEmailAddress());
                return ClerkWebhookResponse.USER_ALREADY_EXISTS;
            }

            User newUser = User.builder()
                    .clerkId(clerkUser.getClerkId())
                    .emailAddress(clerkUser.getEmailAddress())
                    .fullName(clerkUser.getFullName())
                    .bookcase(new Bookcase())
                    .build();

            newUser.getBookcase().setUser(newUser);
            userRepository.save(newUser);
            logger.info("Created new user: {}", newUser);
            return ClerkWebhookResponse.USER_CREATED_SUCCESS;
        } catch (Exception ex) {
            logger.error("Error creating user with clerkId {}: {}", clerkUser.getClerkId(), ex.getMessage(), ex);
            throw new UserCreationException("Failed to create user", ex);
        }
    }

    public ClerkWebhookResponse deleteUserFromDatabase(JsonNode userData) throws Exception {
        ClerkUserData clerkUser = clerkWebhookUtil.extractUserData(userData);

        try {
            Optional<User> optionalUser = userRepository.findByClerkId(clerkUser.getClerkId());

            if(optionalUser.isEmpty()){
                logger.info("User with clerkId {} not found in database...",
                        clerkUser.getClerkId());
                return ClerkWebhookResponse.USER_DOESNT_EXIST;
            }

            User foundUser = optionalUser.get();
            userRepository.delete(foundUser);
            logger.info("Deleted user {} from Booktok Database...", clerkUser);
            return ClerkWebhookResponse.USER_DELETED_SUCCESS;
        } catch (Exception ex){
            logger.error("Error deleting user {}", ex.getMessage());
            throw new Exception(ex.getMessage());
        }
    }

    public ClerkWebhookResponse updateUserInDatabase(JsonNode userData) throws Exception {
        ClerkUserData clerkUser = clerkWebhookUtil.extractUserData(userData);

        try {
            Optional<User> optionalUser = userRepository.findByClerkId(clerkUser.getClerkId());

            if (optionalUser.isEmpty()) {
                logger.info("User with Clerk Id {} does not exist in database.", clerkUser.getClerkId());
                return ClerkWebhookResponse.USER_DOESNT_EXIST;
            }

            User foundUser = optionalUser.get();
            foundUser.setFullName(clerkUser.getFullName());
            foundUser.setEmailAddress(clerkUser.getEmailAddress());
            userRepository.save(foundUser);

            logger.info("Updated user: {}", foundUser);
            return ClerkWebhookResponse.USER_UPDATED_SUCCESS;
        } catch (Exception ex) {
            logger.error("Error updating user with Clerk Id {}: {}", clerkUser.getClerkId(), ex.getMessage(), ex);
            throw new Exception(ex.getMessage());
        }
    }

}
