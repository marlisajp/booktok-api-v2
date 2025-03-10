package com.marlisajp.booktok_api_v2.auth;

import com.marlisajp.booktok_api_v2.clerk.ClerkWebhookResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class AuthorizeUserService {
    private final ClerkTokenService clerkTokenService;
    private static final Logger logger = LoggerFactory.getLogger(AuthorizeUserService.class);

    public AuthorizeUserService(ClerkTokenService clerkTokenService){
        this.clerkTokenService = clerkTokenService;
    }

    public ClerkWebhookResponse authorize(String clerkId, Jwt jwt){
        String clerkIdFromToken = clerkTokenService.getClerkIdFromToken(jwt);

        if(!clerkIdFromToken.equals(clerkId)){
            logger.info("User is not authorized to access resource");
            return ClerkWebhookResponse.USER_NOT_AUTHORIZED;
        } else {
            logger.info("User is authorized to access resource");
            return ClerkWebhookResponse.USER_AUTHORIZED;
        }
    }
}
