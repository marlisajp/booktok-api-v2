package com.marlisajp.booktok_api_v2.user;

import com.marlisajp.booktok_api_v2.book.Book;
import com.marlisajp.booktok_api_v2.book.BookRepository;
import com.marlisajp.booktok_api_v2.book.BookService;
import com.marlisajp.booktok_api_v2.bookcase.Bookcase;
import com.marlisajp.booktok_api_v2.bookcase.BookcaseRepository;
import com.marlisajp.booktok_api_v2.dto.author.AuthorDTO;
import com.marlisajp.booktok_api_v2.dto.book.BookDTO;
import com.marlisajp.booktok_api_v2.dto.bookcase.BookcaseDTO;
import com.marlisajp.booktok_api_v2.exception.GenericException;
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

    public UserService(UserRepository userRepository,BookRepository bookRepository, BookcaseRepository bookcaseRepository){
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.bookcaseRepository = bookcaseRepository;
    }

    public BookcaseDTO getUserBookcaseByClerkId(String clerkId) throws Exception {
        Bookcase bookcase = getUser(clerkId).getBookcase();
        return mapToDto(bookcase);
    }

    public BookcaseDTO addBookToUserBookcase(Long bookId, String clerkId) throws Exception, GenericException {
        Bookcase bookcase = getUser(clerkId).getBookcase();

        if (bookcase == null) {
            throw new Exception("Bookcase doesn't exist for this user");
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new Exception("Book doesn't exist"));

        boolean bookExistsInBookcase = bookcase.getBooks().contains(book);

        if(bookExistsInBookcase){
            throw new GenericException(HttpStatus.BAD_REQUEST,"Book is in users bookcase already.");
        }

        bookcase.getBooks().addFirst(book);

        bookcaseRepository.save(bookcase);
        return mapToDto(bookcase);
    }
    
    public BookcaseDTO deleteBookFromUserBookcase(Long bookId, String clerkId) throws Exception {
        Bookcase bookcase = getUser(clerkId).getBookcase();

        if (bookcase == null) {
            throw new Exception("Bookcase doesn't exist for this user");
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new Exception("Book doesn't exist"));

        bookcase.getBooks().remove(book);
        bookcaseRepository.save(bookcase);
        return mapToDto(bookcase);
    }

    private User getUser(String clerkId) throws Exception {
        return userRepository.findByClerkId(clerkId)
                .orElseThrow(() -> new Exception("User doesn't exist..."));
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
