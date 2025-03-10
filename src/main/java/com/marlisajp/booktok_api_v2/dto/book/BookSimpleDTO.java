package com.marlisajp.booktok_api_v2.dto.book;

public class BookSimpleDTO {
    private Long id;
    private String title;
    private String imageUrl;
    private String description;
    private String genre;

    public BookSimpleDTO() {
    }

    public BookSimpleDTO(Long id, String title, String imageUrl, String description, String genre) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
        this.genre = genre;
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
}
