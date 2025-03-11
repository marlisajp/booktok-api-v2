package com.marlisajp.booktok_api_v2.auth;

import com.marlisajp.booktok_api_v2.exception.GenericException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class AuthorizeUserService {
    private final ClerkTokenService clerkTokenService;
    private static final Logger logger = LoggerFactory.getLogger(AuthorizeUserService.class);

    public AuthorizeUserService(ClerkTokenService clerkTokenService){
        this.clerkTokenService = clerkTokenService;
    }

    public void authorize(String clerkId, Jwt jwt){
        String clerkIdFromToken = clerkTokenService.getClerkIdFromToken(jwt);

        if(!clerkIdFromToken.equals(clerkId)){
            logger.error("User {} is not authorized to access resource",clerkId);
            throw new GenericException(
                    HttpStatus.UNAUTHORIZED,
                    HttpStatus.UNAUTHORIZED.value(),
                    "User is not authorized to access this resource");
        } else {
            logger.info("User has been authorized to access resource");
        }
    }
}
