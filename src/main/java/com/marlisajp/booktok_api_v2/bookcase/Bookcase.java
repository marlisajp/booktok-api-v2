package com.marlisajp.booktok_api_v2.bookcase;

import com.marlisajp.booktok_api_v2.book.Book;
import com.marlisajp.booktok_api_v2.user.User;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "bookcase")
public class Bookcase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(mappedBy = "bookcase", fetch = FetchType.LAZY)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "bookcase_book",
            joinColumns = @JoinColumn(name = "bookcase_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> books;

    public Bookcase() {
    }

    public Bookcase(Long id, User user, List<Book> books) {
        this.id = id;
        this.user = user;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private Long id;
        private User user;
        private List<Book> books;

        public Builder id(Long id){
            this.id = id;
            return this;
        }

        public Builder user(User user){
            this.user = user;
            return this;
        }

        public Builder books(List<Book> books){
            this.books = books;
            return this;
        }

        public Bookcase build(){
            return new Bookcase(id, user, books);
        }
    }
}
