package com.marlisajp.booktok_api_v2.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marlisajp.booktok_api_v2.auditable.Auditable;
import com.marlisajp.booktok_api_v2.post.Post;
import com.marlisajp.booktok_api_v2.user.User;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "comment")
public class Comment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Comment() {
    }

    public Comment(Long id, String content, Post post, User user) {
        this.id = id;
        this.content = content;
        this.post = post;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String content;
        private Post post;
        private User user;

        public Builder id(Long id){
            this.id = id;
            return this;
        }

        public Builder content(String content){
            this.content = content;
            return this;
        }

        public Builder post(Post post){
            this.post = post;
            return this;
        }

        public Builder user(User user){
            this.user = user;
            return this;
        }

        public Comment build() {
            return new Comment(id, content, post, user);
        }

    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", post=" + post +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id)
                && Objects.equals(content, comment.content)
                && Objects.equals(post, comment.post)
                && Objects.equals(user, comment.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, post, user);
    }
}
