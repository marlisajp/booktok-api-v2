package com.marlisajp.booktok_api_v2.user;

import com.marlisajp.booktok_api_v2.auth.AuthorizeUserService;
import com.marlisajp.booktok_api_v2.book.Book;
import com.marlisajp.booktok_api_v2.book.BookRepository;
import com.marlisajp.booktok_api_v2.book.BookService;
import com.marlisajp.booktok_api_v2.bookcase.Bookcase;
import com.marlisajp.booktok_api_v2.bookcase.BookcaseRepository;
import com.marlisajp.booktok_api_v2.dto.author.AuthorDTO;
import com.marlisajp.booktok_api_v2.dto.book.BookDTO;
import com.marlisajp.booktok_api_v2.dto.bookcase.BookcaseDTO;
import com.marlisajp.booktok_api_v2.exception.GenericException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BookcaseRepository bookcaseRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    public UserService(UserRepository userRepository,BookRepository bookRepository, BookcaseRepository bookcaseRepository){
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.bookcaseRepository = bookcaseRepository;
    }

    public BookcaseDTO getUserBookcaseByClerkId(String clerkId) {
        Bookcase bookcase = getUser(clerkId).getBookcase();
        logger.info("User successfully retrieved their bookcase");
        return mapToDto(bookcase);
    }

    public BookcaseDTO addBookToUserBookcase(Long bookId, String clerkId) {
        Bookcase bookcase = getUser(clerkId).getBookcase();

        if (bookcase == null) {
            throw new GenericException(HttpStatus.NOT_FOUND,
                    HttpStatus.NOT_FOUND.value(),
                    "No bookcase was found");
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND,
                        HttpStatus.NOT_FOUND.value(),
                        "Book does not exist"));

        boolean bookExistsInBookcase = bookcase.getBooks().contains(book);

        if(bookExistsInBookcase){
            throw new GenericException(HttpStatus.BAD_REQUEST,
                    HttpStatus.BAD_REQUEST.value(),
                    "Book already in bookcase");
        }

        bookcase.getBooks().addFirst(book);
        bookcaseRepository.save(bookcase);
        logger.info("User successfully added book to their bookcase");
        return mapToDto(bookcase);
    }
    
    public BookcaseDTO deleteBookFromUserBookcase(Long bookId, String clerkId){
        Bookcase bookcase = getUser(clerkId).getBookcase();

        if (bookcase == null) {
            throw new GenericException(HttpStatus.NOT_FOUND,
                    HttpStatus.NOT_FOUND.value(),
                    "No bookcase was found");
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND,
                        HttpStatus.NOT_FOUND.value(),
                        "Book does not exist"));

        boolean bookInBookcase = bookcase.getBooks().contains(book);

        if(!bookInBookcase){
            throw new GenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(),
                    "Book is not in this bookcase. Cannot delete.");
        }

        bookcase.getBooks().remove(book);
        bookcaseRepository.save(bookcase);
        logger.info("User successfully deleted book from their bookcase");
        return mapToDto(bookcase);
    }

    private User getUser(String clerkId) {
        return userRepository.findByClerkId(clerkId)
                .orElseThrow(() -> new GenericException(
                        HttpStatus.NOT_FOUND,
                        HttpStatus.NOT_FOUND.value(),
                        "User doesnt exist."));
    }

    private BookcaseDTO mapToDto(Bookcase bookcase){
        List<Book> books = bookcase.getBooks();
        List<BookDTO> bookDTOs = books
                .stream()
                .map(book -> {
                    AuthorDTO authorDTO = new AuthorDTO(
                        book.getAuthor().getId(),
                        book.getAuthor().getName(),
                        book.getAuthor().getImageUrl(),
                        book.getAuthor().getBiography()
                    );

                    return new BookDTO(
                            book.getId(),
                            book.getTitle(),
                            book.getImageUrl(),
                            book.getDescription(),
                            book.getGenre(),
                            authorDTO
                    );
                })
                .toList();

        return new BookcaseDTO(bookcase.getId(), bookDTOs);
    }
}
