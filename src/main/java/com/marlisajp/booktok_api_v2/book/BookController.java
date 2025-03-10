package com.marlisajp.booktok_api_v2.book;

import com.marlisajp.booktok_api_v2.dto.book.BookDTO;
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
    public ResponseEntity<BookDTO> findBookById(@PathVariable("id") Long id){
        return bookService.findBookById(id)
                .map(book -> ResponseEntity.status(HttpStatus.OK).body(book))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookDTO>> findAllBooks(){
        List<BookDTO> books = bookService.findAllBooks();
        return ResponseEntity.ok(books);
    }
}
