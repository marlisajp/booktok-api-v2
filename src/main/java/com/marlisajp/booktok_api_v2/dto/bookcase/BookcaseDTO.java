package com.marlisajp.booktok_api_v2.dto.bookcase;

import com.marlisajp.booktok_api_v2.dto.book.BookDTO;

import java.util.List;

public class BookcaseDTO {
    private Long id;
    private List<BookDTO> books;

    public BookcaseDTO(Long id, List<BookDTO> books){
        this.id = id;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }
}
