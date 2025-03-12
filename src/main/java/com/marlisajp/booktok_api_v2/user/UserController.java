package com.marlisajp.booktok_api_v2.user;

import com.marlisajp.booktok_api_v2.auth.AuthorizeUserService;
import com.marlisajp.booktok_api_v2.comment.CommentRequest;
import com.marlisajp.booktok_api_v2.comment.CommentService;
import com.marlisajp.booktok_api_v2.dto.bookcase.BookcaseDTO;
import com.marlisajp.booktok_api_v2.dto.comment.CommentDTO;
import com.marlisajp.booktok_api_v2.dto.post.PostDTO;
import com.marlisajp.booktok_api_v2.post.PostRequest;
import com.marlisajp.booktok_api_v2.post.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final AuthorizeUserService authorizeUserService;
    private final PostService postService;
    private final CommentService commentService;
    public UserController(UserService userService, AuthorizeUserService authorizeUserService, PostService postService, CommentService commentService){
        this.userService = userService;
        this.authorizeUserService = authorizeUserService;
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("/{clerkId}/bookcase")
    public ResponseEntity<BookcaseDTO> getUserBookcaseByClerkId(@PathVariable("clerkId") String clerkId, @AuthenticationPrincipal Jwt jwt)  {
        authorizeUserService.authorize(clerkId, jwt);
        return ResponseEntity.ok(userService.getUserBookcaseByClerkId(clerkId));
    }

    @PostMapping("/{clerkId}/bookcase/add/{bookId}")
    public ResponseEntity<BookcaseDTO> addBooktoUserBookcase(@PathVariable("clerkId") String clerkId, @PathVariable("bookId") Long bookId, @AuthenticationPrincipal Jwt jwt) {
        authorizeUserService.authorize(clerkId,jwt);
        BookcaseDTO updatedBookcase = userService.addBookToUserBookcase(bookId, clerkId);
        return ResponseEntity.ok(updatedBookcase);
    }

    @DeleteMapping("/{clerkId}/bookcase/delete/{bookId}")
    public ResponseEntity<BookcaseDTO> deleteBookFromUserBookcase(@PathVariable("clerkId") String clerkId, @PathVariable("bookId") Long bookId, @AuthenticationPrincipal Jwt jwt)  {
        authorizeUserService.authorize(clerkId,jwt);
        BookcaseDTO updatedBookcase = userService.deleteBookFromUserBookcase(bookId, clerkId);
        return ResponseEntity.ok(updatedBookcase);
    }

    @PostMapping("/{clerkId}/posts/add")
    public ResponseEntity<PostDTO> createPost(@PathVariable("clerkId") String clerkId, @RequestBody PostRequest postRequest, @AuthenticationPrincipal Jwt jwt){
        authorizeUserService.authorize(clerkId,jwt);
        PostDTO postDTO = postService.createPost(postRequest, clerkId);
        return ResponseEntity.ok(postDTO);
    }

    @PostMapping("/{clerkId}/posts/add-comment")
    public ResponseEntity<CommentDTO> addCommentToPost(@PathVariable("clerkId") String clerkId, @RequestBody CommentRequest commentRequest, @AuthenticationPrincipal Jwt jwt){
        authorizeUserService.authorize(clerkId,jwt);
        CommentDTO commentDTO = commentService.addCommentToPost(commentRequest, clerkId);
        return ResponseEntity.ok(commentDTO);
    }
}
