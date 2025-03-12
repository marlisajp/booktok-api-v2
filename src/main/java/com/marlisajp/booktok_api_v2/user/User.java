package com.marlisajp.booktok_api_v2.user;

import com.marlisajp.booktok_api_v2.bookcase.Bookcase;
import com.marlisajp.booktok_api_v2.comment.Comment;
import com.marlisajp.booktok_api_v2.post.Post;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String clerkId;
    private String emailAddress;
    private String username;
    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @JoinColumn(name = "bookcase_id")
    private Bookcase bookcase;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    public User() {
    }

    public User(Long id, String clerkId, String emailAddress, String username, Bookcase bookcase, List<Post> posts, List<Comment> comments) {
        this.id = id;
        this.clerkId = clerkId;
        this.emailAddress = emailAddress;
        this.username = username;
        this.bookcase = bookcase;
        this.posts = posts;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClerkId() {
        return clerkId;
    }

    public void setClerkId(String clerkId) {
        this.clerkId = clerkId;
    }

    public Bookcase getBookcase() {
        return bookcase;
    }

    public void setBookcase(Bookcase bookcase) {
        this.bookcase = bookcase;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String clerkId;
        private String emailAddress;
        private String username;
        private Bookcase bookcase;
        private List<Post> posts;
        private List<Comment> comments;

        public Builder id(Long id){
            this.id = id;
            return this;
        }

        public Builder clerkId(String clerkId){
            this.clerkId = clerkId;
            return this;
        }

        public Builder emailAddress(String emailAddress){
            this.emailAddress = emailAddress;
            return this;
        }

        public Builder username(String username){
            this.username = username;
            return this;
        }

        public Builder bookcase(Bookcase bookcase){
            this.bookcase = bookcase;
            return this;
        }

        public Builder posts(List<Post> posts){
            this.posts = posts;
            return this;
        }

        public Builder comments(List<Comment> comments){
            this.comments = comments;
            return this;
        }

        public User build(){
            return new User(id, clerkId, emailAddress, username, bookcase, posts,comments);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", clerkId='" + clerkId + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", username='" + username + '\'' +
                ", bookcase=" + bookcase.toString() +
                ", posts=" + posts.toString() +
                ", comments=" + comments.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id)
                && Objects.equals(clerkId, user.clerkId)
                && Objects.equals(emailAddress, user.emailAddress)
                && Objects.equals(username, user.username)
                && Objects.equals(bookcase, user.bookcase)
                && Objects.equals(posts, user.posts)
                && Objects.equals(comments, user.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clerkId, emailAddress, username, bookcase, posts, comments);
    }
}
