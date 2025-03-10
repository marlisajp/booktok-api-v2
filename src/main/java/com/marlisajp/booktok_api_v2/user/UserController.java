package com.marlisajp.booktok_api_v2.user;

import com.marlisajp.booktok_api_v2.auth.AuthorizeUserService;
import com.marlisajp.booktok_api_v2.clerk.ClerkWebhookResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final AuthorizeUserService authorizeUserService;

    public UserController(UserService userService, AuthorizeUserService authorizeUserService){
        this.userService = userService;
        this.authorizeUserService = authorizeUserService;
    }

    @GetMapping("/{clerkId}/bookcase")
    public ResponseEntity<?> getUserBookcaseByClerkId(@PathVariable("clerkId") String clerkId, @AuthenticationPrincipal Jwt jwt) throws Exception {
        ClerkWebhookResponse clerkResponse = authorizeUserService.authorize(clerkId, jwt);

        if(clerkResponse.equals(ClerkWebhookResponse.USER_NOT_AUTHORIZED)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authorized! Scram!");
        }

        return userService.getUserBookcaseByClerkId(clerkId)
                .map(bookcase -> ResponseEntity.ok().body(bookcase))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
