package com.marlisajp.booktok_api_v2.dto.post;

import com.marlisajp.booktok_api_v2.dto.comment.CommentDTO;

import java.time.Instant;
import java.util.List;

public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private List<CommentDTO> comments;
    private String username;
    private String bookTitle;
    private String bookAuthor;
    private Instant createdAt;

    public PostDTO() {
    }

    public PostDTO(Long id, String title, String content, List<CommentDTO> comments, String username, String bookTitle, String bookAuthor, Instant createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.comments = comments;
        this.username = username;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
