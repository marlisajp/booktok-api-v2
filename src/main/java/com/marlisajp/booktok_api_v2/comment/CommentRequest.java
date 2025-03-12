package com.marlisajp.booktok_api_v2.comment;

public class CommentRequest {
    private String content;
    private Long postId;

    public CommentRequest() {
    }

    public CommentRequest(String content, Long postId) {
        this.content = content;
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPostId(){
        return postId;
    }

    public void setPostId(Long postId){
        this.postId = postId;
    }
}
