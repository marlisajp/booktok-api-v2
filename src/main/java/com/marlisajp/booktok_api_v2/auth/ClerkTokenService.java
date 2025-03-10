package com.marlisajp.booktok_api_v2.auth;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class ClerkTokenService {
    public String getClerkIdFromToken(Jwt jwt){
        return jwt.getSubject();
    }
}
