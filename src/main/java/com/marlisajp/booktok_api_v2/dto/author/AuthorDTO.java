package com.marlisajp.booktok_api_v2.dto.author;

public class AuthorDTO {
    private Long id;
    private String name;
    private String imageUrl;
    private String biography;

    public AuthorDTO() {
    }

    public AuthorDTO(Long id, String name, String imageUrl, String biography) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.biography = biography;
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
}
