package com.marlisajp.booktok_api_v2.comment;

import com.marlisajp.booktok_api_v2.dto.comment.CommentDTO;
import com.marlisajp.booktok_api_v2.exception.GenericException;
import com.marlisajp.booktok_api_v2.post.Post;
import com.marlisajp.booktok_api_v2.post.PostRepository;
import com.marlisajp.booktok_api_v2.user.User;
import com.marlisajp.booktok_api_v2.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public CommentDTO addCommentToPost(CommentRequest commentRequest, String clerkId){
        if(commentRequest == null
                || commentRequest.getPostId() == null
                || commentRequest.getContent() == null
                || commentRequest.getContent().trim().isEmpty()
        ) {
            throw new GenericException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), "Comment request cannot be empty or have empty fields.");
        }

        Long postId = commentRequest.getPostId();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), "Post does not exist."
        ));

        User user = userRepository.findByClerkId(clerkId)
                .orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), "User does not exist."));

        Comment comment = Comment.builder()
                .content(commentRequest.getContent())
                .post(post)
                .user(user)
                .build();

        post.getComments().addLast(comment);
        user.getComments().add(comment);

        commentRepository.save(comment);
        postRepository.save(post);
        userRepository.save(user);
        logger.info("User {} created a new comment for post {}", user.getClerkId(), post.getId());
        return mapToDto(comment);
    }

    private CommentDTO mapToDto(Comment comment) {
        return new CommentDTO(
                comment.getId(), comment.getContent(), comment.getUser().getUsername(), comment.getCreatedAt()
        );
    }
}
