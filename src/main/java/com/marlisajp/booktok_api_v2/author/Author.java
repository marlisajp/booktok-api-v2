package com.marlisajp.booktok_api_v2.author;

import com.marlisajp.booktok_api_v2.book.Book;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String imageUrl;
    private String biography;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Book> books;

    public Author() {
    }

    public Author(Long id, String name, String imageUrl, String biography, List<Book> books) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.biography = biography;
        this.books = books;
    }

    public Author(String name, String imageUrl, String biography, List<Book> books) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
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

    public static class Builder {
        private Long id;
        private String name;
        private String imageUrl;
        private String biography;
        private List<Book> books;

        public Builder id(Long id){
            this.id = id;
            return this;
        }

       public Builder name(String name){
           this.name = name;
           return this;
       }

       public Builder imageUrl(String imageUrl){
           this.imageUrl = imageUrl;
           return this;
       }

       public Builder biography(String biography){
           this.biography = biography;
           return this;
       }

       public Builder books(List<Book> books){
           this.books = books;
           return this;
       }

       public Author build(){
           return new Author(id, name, imageUrl, biography, books);
       }
    }
}
