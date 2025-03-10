package com.marlisajp.booktok_api_v2.book;

import com.marlisajp.booktok_api_v2.author.Author;
import com.marlisajp.booktok_api_v2.dto.author.AuthorDTO;
import com.marlisajp.booktok_api_v2.dto.book.BookDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Optional<BookDTO> findBookById(Long id){
        return bookRepository.findById(id)
                .map(this::mapToDto);
    }

    public List<BookDTO> findAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private BookDTO mapToDto(Book book) {
        Author author = book.getAuthor();
        AuthorDTO authorDTO = new AuthorDTO(
                author.getId(),
                author.getName(),
                author.getImageUrl(),
                author.getBiography()
        );

        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getImageUrl(),
                book.getDescription(),
                book.getGenre(),
                authorDTO
        );
    }
}
