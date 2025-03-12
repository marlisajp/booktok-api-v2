package com.marlisajp.booktok_api_v2.author;

import com.marlisajp.booktok_api_v2.dto.author.AuthorDTO;
import com.marlisajp.booktok_api_v2.dto.author.AuthorDetailDTO;
import com.marlisajp.booktok_api_v2.dto.bookcase.BookcaseDTO;
import com.marlisajp.booktok_api_v2.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public/api/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/{id}")
    public ApiResponse<AuthorDetailDTO> findAuthorById(@PathVariable("id") Long id){
        AuthorDetailDTO author = authorService.findAuthorById(id);
        return new ApiResponse<AuthorDetailDTO>(
                HttpStatus.OK,
                HttpStatus.OK.value(),
                "Retrieved author with id: " + id,
                true,
                author
        );
    }

    @GetMapping("/all")
    public ApiResponse<List<AuthorDTO>> findAllAuthors(){
        List<AuthorDTO> authors = authorService.findAllAuthors();
        return new ApiResponse<List<AuthorDTO>>(
                HttpStatus.OK,
                HttpStatus.OK.value(),
                "Retrieved all authors",
                true,
                authors
        );
    }
}
