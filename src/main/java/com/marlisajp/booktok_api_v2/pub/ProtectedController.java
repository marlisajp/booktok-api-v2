package com.marlisajp.booktok_api_v2.pub;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/protected")
public class ProtectedController {
    @GetMapping("/ping")
    public String protectedEndpoint(@AuthenticationPrincipal Jwt jwt) {
        return "Hello, " + jwt.getSubject() + "! You have accessed a protected endpoint.";
    }
}
