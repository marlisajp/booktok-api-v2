package com.marlisajp.booktok_api_v2.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Configuration
public class EnvConfig {
    @Value("${CLERK_SIGNING_SECRET}")
    private String CLERK_SIGNING_SECRET;

    @Value("${JWKS_URI}")
    private String JWKS_URI;

    @Value("${JWT_ISSUER_URI}")
    private String JWT_ISSUER_URI;

    @PostConstruct
    public void init() {
//        System.out.println("SYSTEM_CLERK from Spring: " + CLERK_SIGNING_SECRET);
//        System.out.println("JWKS_URI from Spring: " + JWKS_URI);
//        System.out.println("JWT_ISSUER_URI from Spring: " + JWT_ISSUER_URI);
    }
}
