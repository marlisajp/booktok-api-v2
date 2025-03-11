package com.marlisajp.booktok_api_v2.user;

import com.marlisajp.booktok_api_v2.auth.AuthorizeUserService;
import com.marlisajp.booktok_api_v2.clerk.ClerkWebhookResponse;
import com.marlisajp.booktok_api_v2.dto.bookcase.BookcaseDTO;
import com.marlisajp.booktok_api_v2.exception.ErrorResponse;
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
    public ResponseEntity<BookcaseDTO> getUserBookcaseByClerkId(@PathVariable("clerkId") String clerkId,
                                                      @AuthenticationPrincipal Jwt jwt)  {
        authorizeUserService.authorize(clerkId, jwt);
        return ResponseEntity.ok(userService.getUserBookcaseByClerkId(clerkId));

    }

    @PostMapping("/{clerkId}/bookcase/add/{bookId}")
    public ResponseEntity<BookcaseDTO> addBooktoUserBookcase(@PathVariable("clerkId") String clerkId, @PathVariable("bookId") Long bookId,
                                                   @AuthenticationPrincipal Jwt jwt) {
        authorizeUserService.authorize(clerkId,jwt);
        BookcaseDTO updatedBookcase = userService.addBookToUserBookcase(bookId, clerkId);
        return ResponseEntity.ok(updatedBookcase);

    }

    @DeleteMapping("/{clerkId}/bookcase/delete/{bookId}")
    public ResponseEntity<?> deleteBookFromUserBookcase(@PathVariable("clerkId") String clerkId, @PathVariable("bookId") Long bookId, @AuthenticationPrincipal Jwt jwt)  {
        authorizeUserService.authorize(clerkId,jwt);
        BookcaseDTO updatedBookcase = userService.deleteBookFromUserBookcase(bookId, clerkId);
        return ResponseEntity.ok(updatedBookcase);
    }
}
