package com.marlisajp.booktok_api_v2.author;

import com.marlisajp.booktok_api_v2.book.Book;
import com.marlisajp.booktok_api_v2.dto.author.AuthorDTO;
import com.marlisajp.booktok_api_v2.dto.author.AuthorDetailDTO;
import com.marlisajp.booktok_api_v2.dto.book.BookSimpleDTO;
import com.marlisajp.booktok_api_v2.exception.GenericException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    public AuthorDetailDTO findAuthorById(Long id){
        return authorRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), "Author not found."));
    }

    public List<AuthorDTO> findAllAuthors(){
        return authorRepository.findAll()
                .stream()
                .map(this::mapToSimpleDto)
                .collect(Collectors.toList());
    }

    private AuthorDetailDTO mapToDto(Author author){
        List<Book> books = author.getBooks();

        List<BookSimpleDTO> booksSimpleDTOS =  books
                .stream()
                .map(book -> new BookSimpleDTO(
                        book.getId(),
                        book.getTitle(),
                        book.getDescription(),
                        book.getImageUrl(),
                        book.getGenre()))
                .collect(Collectors.toList());

        return new AuthorDetailDTO(
                author.getId(),
                author.getName(),
                author.getBiography(),
                author.getImageUrl(),
                booksSimpleDTOS
        );
    }

    private AuthorDTO mapToSimpleDto(Author author){
        return new AuthorDTO(
                author.getId(),
                author.getName(),
                author.getImageUrl(),
                author.getBiography()
        );
    }
}
