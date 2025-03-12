package com.marlisajp.booktok_api_v2.user;

import com.marlisajp.booktok_api_v2.auth.AuthorizeUserService;
import com.marlisajp.booktok_api_v2.clerk.ClerkWebhookResponse;
import com.marlisajp.booktok_api_v2.comment.CommentRequest;
import com.marlisajp.booktok_api_v2.comment.CommentService;
import com.marlisajp.booktok_api_v2.dto.bookcase.BookcaseDTO;
import com.marlisajp.booktok_api_v2.dto.comment.CommentDTO;
import com.marlisajp.booktok_api_v2.dto.post.PostDTO;
import com.marlisajp.booktok_api_v2.post.Post;
import com.marlisajp.booktok_api_v2.post.PostRequest;
import com.marlisajp.booktok_api_v2.post.PostService;
import com.marlisajp.booktok_api_v2.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ApiResponse<BookcaseDTO> getUserBookcaseByClerkId(@PathVariable("clerkId") String clerkId, @AuthenticationPrincipal Jwt jwt)  {
        authorizeUserService.authorize(clerkId, jwt);
        BookcaseDTO bookcaseDTO = userService.getUserBookcaseByClerkId(clerkId);
        return new ApiResponse<BookcaseDTO>(
                HttpStatus.OK,
                HttpStatus.OK.value(),
                "Retrieved user's bookcase by clerk ID",
                true,
                bookcaseDTO
        );
    }

    @PostMapping("/{clerkId}/bookcase/add/{bookId}")
    public ApiResponse<BookcaseDTO> addBooktoUserBookcase(@PathVariable("clerkId") String clerkId, @PathVariable("bookId") Long bookId, @AuthenticationPrincipal Jwt jwt) {
        authorizeUserService.authorize(clerkId,jwt);
        BookcaseDTO updatedBookcase = userService.addBookToUserBookcase(bookId, clerkId);
        return new ApiResponse<BookcaseDTO>(
                HttpStatus.CREATED,
                HttpStatus.CREATED.value(),
                "Added book to user's bookcase",
                true,
                updatedBookcase
        );
    }

    @DeleteMapping("/{clerkId}/bookcase/delete/{bookId}")
    public ApiResponse<BookcaseDTO> deleteBookFromUserBookcase(@PathVariable("clerkId") String clerkId, @PathVariable("bookId") Long bookId, @AuthenticationPrincipal Jwt jwt)  {
        authorizeUserService.authorize(clerkId,jwt);
        BookcaseDTO updatedBookcase = userService.deleteBookFromUserBookcase(bookId, clerkId);
        return new ApiResponse<BookcaseDTO>(
                HttpStatus.ACCEPTED,
                HttpStatus.ACCEPTED.value(),
                "Deleted book from user's bookcase",
                true,
                updatedBookcase
        );
    }

    @GetMapping("/{clerkId}/posts/all")
    public ApiResponse<List<PostDTO>> getUsersPosts(@PathVariable("clerkId") String clerkId, @AuthenticationPrincipal Jwt jwt){
        authorizeUserService.authorize(clerkId,jwt);
        List<PostDTO> postDTOS = postService.getUsersPosts(clerkId);
        return new ApiResponse<List<PostDTO>>(
                HttpStatus.OK,
                HttpStatus.OK.value(),
                "Retrieved all posts belonging to user",
                true,
                postDTOS
        );
    }


    @PostMapping("/{clerkId}/posts/add")
    public ApiResponse<PostDTO> createPost(@PathVariable("clerkId") String clerkId, @RequestBody PostRequest postRequest, @AuthenticationPrincipal Jwt jwt){
        authorizeUserService.authorize(clerkId,jwt);
        PostDTO postDTO = postService.createPost(postRequest, clerkId);
        return new ApiResponse<PostDTO>(
                HttpStatus.CREATED,
                HttpStatus.CREATED.value(),
                "Created a new post",
                true,
                postDTO
        );
    }

    @DeleteMapping("/{clerkId}/posts/delete/{postId}")
    public ApiResponse<ClerkWebhookResponse> deletePost(@PathVariable("clerkId") String clerkId, @PathVariable("postId") Long postId, @AuthenticationPrincipal Jwt jwt){
        authorizeUserService.authorize(clerkId,jwt);
        ClerkWebhookResponse response = postService.deletePost(clerkId, postId);
        return new ApiResponse<ClerkWebhookResponse>(
                HttpStatus.OK,
                HttpStatus.OK.value(),
                "Deleted user's post",
                true,
                response
        );
    }

    @PostMapping("/{clerkId}/posts/add-comment")
    public ApiResponse<CommentDTO> addCommentToPost(@PathVariable("clerkId") String clerkId, @RequestBody CommentRequest commentRequest, @AuthenticationPrincipal Jwt jwt){
        authorizeUserService.authorize(clerkId,jwt);
        CommentDTO commentDTO = commentService.addCommentToPost(commentRequest, clerkId);
        return new ApiResponse<CommentDTO>(
                HttpStatus.CREATED,
                HttpStatus.CREATED.value(),
                "Created new comment under post",
                true,
                commentDTO
        );
    }
}
