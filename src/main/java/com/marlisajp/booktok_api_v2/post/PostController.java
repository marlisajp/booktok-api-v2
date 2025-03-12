package com.marlisajp.booktok_api_v2.post;

import com.marlisajp.booktok_api_v2.dto.post.PostDTO;
import com.marlisajp.booktok_api_v2.exception.GenericException;
import com.marlisajp.booktok_api_v2.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/all")
    public ApiResponse<List<PostDTO>> getAllPosts(@AuthenticationPrincipal Jwt jwt){
        if(jwt == null){
            throw new GenericException(HttpStatus.UNAUTHORIZED,
                    HttpStatus.UNAUTHORIZED.value(),
                    "User must be logged in to access this resource");
        }
        return postService.getAllUsersPosts();
    }
}
