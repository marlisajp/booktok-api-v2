package com.marlisajp.booktok_api_v2.post;

import com.marlisajp.booktok_api_v2.book.Book;
import com.marlisajp.booktok_api_v2.book.BookRepository;
import com.marlisajp.booktok_api_v2.dto.comment.CommentDTO;
import com.marlisajp.booktok_api_v2.dto.post.PostDTO;
import com.marlisajp.booktok_api_v2.exception.GenericException;
import com.marlisajp.booktok_api_v2.user.User;
import com.marlisajp.booktok_api_v2.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    public PostService(PostRepository postRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public PostDTO createPost(PostRequest postRequest, String clerkId){
        if(postRequest == null
                || postRequest.getTitle().isEmpty()
                || postRequest.getContent().isEmpty()
                || postRequest.getBookId().toString().isEmpty()){
            logger.error("Tried creating a post with empty body or field.");
            throw new GenericException(
                    HttpStatus.BAD_REQUEST,
                    HttpStatus.BAD_REQUEST.value(),
                    "Post request cannot be empty or have empty fields."
            );
        }

        User user = userRepository.findByClerkId(clerkId)
                .orElseThrow(() -> new GenericException(
                        HttpStatus.NOT_FOUND,
                        HttpStatus.NOT_FOUND.value(),
                        "User does not exist."));

        Book book = bookRepository.findById(postRequest.getBookId())
                .orElseThrow(() -> new GenericException(
                        HttpStatus.NOT_FOUND,
                        HttpStatus.NOT_FOUND.value(),
                        "Book does not exist."));

        Post newPost = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .comments(new ArrayList<>())
                .book(book)
                .user(user)
                .build();

        user.getPosts().addFirst(newPost);
        postRepository.save(newPost);
        userRepository.save(user);
        logger.info("User {} created a new post for {}", user.getClerkId(), book.getTitle());
        return mapToDto(newPost);
    }

    public List<PostDTO> getUsersPosts(String clerkId) {
        User user = userRepository.findByClerkId(clerkId)
                .orElseThrow(() -> new GenericException(
                        HttpStatus.NOT_FOUND,
                        HttpStatus.NOT_FOUND.value(),
                        "User does not exist."));
        return user.getPosts().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<PostDTO> getAllUsersPosts() {
        return postRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private PostDTO mapToDto(Post post){
        return new PostDTO(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getComments().stream().map(comment -> new CommentDTO(
                        comment.getId(),
                        comment.getContent(),
                        comment.getUser().getUsername(),
                        comment.getCreatedAt()))
                        .collect(Collectors.toList()),
                post.getUser().getUsername(),
                post.getBook().getTitle(),
                post.getBook().getAuthor().getName(),
                post.getCreatedAt()
        );
    }
}
