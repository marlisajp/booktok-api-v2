package com.marlisajp.booktok_api_v2.user;

import com.marlisajp.booktok_api_v2.book.Book;
import com.marlisajp.booktok_api_v2.bookcase.Bookcase;
import com.marlisajp.booktok_api_v2.dto.author.AuthorDTO;
import com.marlisajp.booktok_api_v2.dto.book.BookDTO;
import com.marlisajp.booktok_api_v2.dto.bookcase.BookcaseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Optional<BookcaseDTO> getUserBookcaseByClerkId(String clerkId) throws Exception {
        Optional<User> optionalUser = userRepository.findByClerkId(clerkId);

        if(optionalUser.isEmpty()){
            throw new Exception("User doesnt exist...");
        }

        return userRepository.findByClerkId(clerkId)
                .map(User::getBookcase)
                .map(this::mapToDto);
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
