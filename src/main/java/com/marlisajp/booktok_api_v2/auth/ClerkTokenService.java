package com.marlisajp.booktok_api_v2.auth;

import com.marlisajp.booktok_api_v2.exception.GenericException;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class ClerkTokenService {
    public String getClerkIdFromToken(Jwt jwt){
        if(jwt == null || jwt.toString().isEmpty()){
            throw new GenericException(
                    HttpStatus.UNAUTHORIZED,
                    HttpStatus.UNAUTHORIZED.value(),
                    "Unauthorized: JWT cannot be empty. "
            );
        }
        return jwt.getSubject();
    }
}
