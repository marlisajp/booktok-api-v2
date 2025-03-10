package com.marlisajp.booktok_api_v2.dto.author;

import com.marlisajp.booktok_api_v2.dto.book.BookSimpleDTO;

import java.util.List;

public class AuthorDetailDTO {
    private Long id;
    private String name;
    private String imageUrl;
    private String biography;
    private List<BookSimpleDTO> books;

    public AuthorDetailDTO() {
    }

    public AuthorDetailDTO(Long id, String name, String imageUrl, String biography, List<BookSimpleDTO> books) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.biography = biography;
        this.books = books;
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

    public List<BookSimpleDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookSimpleDTO> books) {
        this.books = books;
    }
}
