package com.marlisajp.booktok_api_v2.dto.book;

import com.marlisajp.booktok_api_v2.dto.author.AuthorDTO;

public class BookDTO {
    private Long id;
    private String title;
    private String imageUrl;
    private String description;
    private String genre;
    private AuthorDTO author;

    public BookDTO() {
    }

    public BookDTO(Long id, String title, String imageUrl, String description, String genre, AuthorDTO author) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
        this.genre = genre;
        this.author = author;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public AuthorDTO getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDTO author) {
        this.author = author;
    }
}
