package com.marlisajp.booktok_api_v2.user;

import com.marlisajp.booktok_api_v2.auth.AuthorizeUserService;
import com.marlisajp.booktok_api_v2.book.Book;
import com.marlisajp.booktok_api_v2.book.BookService;
import com.marlisajp.booktok_api_v2.clerk.ClerkWebhookResponse;
import com.marlisajp.booktok_api_v2.dto.book.BookDTO;
import com.marlisajp.booktok_api_v2.dto.bookcase.BookcaseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final AuthorizeUserService authorizeUserService;
    private final BookService bookService;

    public UserController(UserService userService, AuthorizeUserService authorizeUserService, BookService bookService){
        this.userService = userService;
        this.authorizeUserService = authorizeUserService;
        this.bookService = bookService;
    }

    @GetMapping("/{clerkId}/bookcase")
    public ResponseEntity<?> getUserBookcaseByClerkId(@PathVariable("clerkId") String clerkId, @AuthenticationPrincipal Jwt jwt) throws Exception {
        ClerkWebhookResponse clerkWebhookResponse = authorizeUserService.authorize(clerkId, jwt);

        if(clerkWebhookResponse.equals(ClerkWebhookResponse.USER_NOT_AUTHORIZED)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authorized! Scram!");
        }

        return userService.getUserBookcaseByClerkId(clerkId)
                .map(bookcase -> ResponseEntity.ok().body(bookcase))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
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
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding book to bookcase: " + ex.getMessage());
        }
    }
}
