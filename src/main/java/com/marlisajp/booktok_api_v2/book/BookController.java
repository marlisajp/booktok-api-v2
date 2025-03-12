package com.marlisajp.booktok_api_v2.book;

import com.marlisajp.booktok_api_v2.dto.book.BookDTO;
import com.marlisajp.booktok_api_v2.dto.comment.CommentDTO;
import com.marlisajp.booktok_api_v2.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    public ApiResponse<BookDTO> findBookById(@PathVariable("id") Long id){
        BookDTO bookDTO = bookService.findBookById(id);
        return new ApiResponse<BookDTO>(
                HttpStatus.OK,
                HttpStatus.OK.value(),
                "Retrieved book with id: " + id,
                true,
                bookDTO
        );
    }

    @GetMapping("/all")
    public ApiResponse<List<BookDTO>> findAllBooks(){
        List<BookDTO> books = bookService.findAllBooks();
        return new ApiResponse<List<BookDTO>>(
                HttpStatus.OK,
                HttpStatus.OK.value(),
                "Retrieved all books",
                true,
                books
        );    }
}
