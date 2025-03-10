package com.marlisajp.booktok_api_v2.user;

import com.marlisajp.booktok_api_v2.bookcase.Bookcase;
import jakarta.persistence.*;

@Entity
@Table(name = "_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String clerkId;
    private String emailAddress;
    private String fullName;
    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @JoinColumn(name = "bookcase_id")
    private Bookcase bookcase;

    public User() {
    }

    public User(Long id, String clerkId, String emailAddress, String fullName, Bookcase bookcase) {
        this.id = id;
        this.clerkId = clerkId;
        this.emailAddress = emailAddress;
        this.fullName = fullName;
        this.bookcase = bookcase;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String clerkId;
        private String emailAddress;
        private String fullName;
        private Bookcase bookcase;

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

        public Builder fullName(String fullName){
            this.fullName = fullName;
            return this;
        }

        public Builder bookcase(Bookcase bookcase){
            this.bookcase = bookcase;
            return this;
        }

        public User build(){
            return new User(id, clerkId, emailAddress, fullName, bookcase);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
