package com.marlisajp.booktok_api_v2.author;

import com.marlisajp.booktok_api_v2.dto.author.AuthorDTO;
import com.marlisajp.booktok_api_v2.dto.author.AuthorDetailDTO;
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
    public ResponseEntity<AuthorDetailDTO> findAuthorById(@PathVariable("id") Long id){
        return authorService.findAuthorById(id)
                .map(author -> ResponseEntity.status(HttpStatus.OK).body(author))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<AuthorDTO>> findAllAuthors(){
        List<AuthorDTO> authors = authorService.findAllAuthors();
        return ResponseEntity.ok(authors);
    }
}
