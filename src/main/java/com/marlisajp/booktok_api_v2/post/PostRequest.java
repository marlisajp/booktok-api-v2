package com.marlisajp.booktok_api_v2.post;

public class PostRequest {
    private String title;
    private String content;
    private Long bookId;

    public PostRequest() {
    }

    public PostRequest(String title, String content, Long bookId) {
        this.title = title;
        this.content = content;
        this.bookId = bookId;
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

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
