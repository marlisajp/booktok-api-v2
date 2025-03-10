package com.marlisajp.booktok_api_v2.user;

import com.marlisajp.booktok_api_v2.auth.AuthorizeUserService;
import com.marlisajp.booktok_api_v2.clerk.ClerkWebhookResponse;
import com.marlisajp.booktok_api_v2.dto.bookcase.BookcaseDTO;
import com.marlisajp.booktok_api_v2.exception.GenericException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final AuthorizeUserService authorizeUserService;
    public UserController(
            UserService userService,
            AuthorizeUserService authorizeUserService){
        this.userService = userService;
        this.authorizeUserService = authorizeUserService;
    }

    @GetMapping("/{clerkId}/bookcase")
    public ResponseEntity<?> getUserBookcaseByClerkId(
            @PathVariable("clerkId") String clerkId,
            @AuthenticationPrincipal Jwt jwt) throws Exception {
        ClerkWebhookResponse clerkWebhookResponse = authorizeUserService.authorize(clerkId, jwt);

        if(clerkWebhookResponse.equals(ClerkWebhookResponse.USER_NOT_AUTHORIZED)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authorized! Scram!");
        }

        return ResponseEntity.ok(userService.getUserBookcaseByClerkId(clerkId));

    }

    @PostMapping("/{clerkId}/bookcase/add/{bookId}")
    public ResponseEntity<?> addBooktoUserBookcase(
            @PathVariable("clerkId") String clerkId,
            @PathVariable("bookId") Long bookId,
            @AuthenticationPrincipal Jwt jwt) throws Exception {
        ClerkWebhookResponse clerkWebhookResponse = authorizeUserService.authorize(clerkId,jwt);

        if(clerkWebhookResponse.equals(ClerkWebhookResponse.USER_NOT_AUTHORIZED)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authorized! Scram!");
        }

        try {
            BookcaseDTO updatedBookcase = userService.addBookToUserBookcase(bookId, clerkId);
            return ResponseEntity.ok(updatedBookcase);
        } catch (GenericException ex){
            return ResponseEntity.status(ex.getStatusCode()).body(ex);
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding book to bookcase: " + ex.getMessage());
        }
    }

    @DeleteMapping("/{clerkId}/bookcase/delete/{bookId}")
    public ResponseEntity<?> deleteBookFromUserBookcase(
            @PathVariable("clerkId") String clerkId,
            @PathVariable("bookId") Long bookId,
            @AuthenticationPrincipal Jwt jwt) throws Exception {
        ClerkWebhookResponse clerkWebhookResponse = authorizeUserService.authorize(clerkId,jwt);

        if(clerkWebhookResponse.equals(ClerkWebhookResponse.USER_NOT_AUTHORIZED)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authorized! Scram!");
        }

        try {
            BookcaseDTO updatedBookcase = userService.deleteBookFromUserBookcase(bookId, clerkId);
            return ResponseEntity.ok(updatedBookcase);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting book to bookcase: " + ex.getMessage());
        }
    }
}
