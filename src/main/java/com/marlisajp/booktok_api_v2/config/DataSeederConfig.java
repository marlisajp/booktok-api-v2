package com.marlisajp.booktok_api_v2.config;

import com.marlisajp.booktok_api_v2.author.Author;
import com.marlisajp.booktok_api_v2.author.AuthorRepository;
import com.marlisajp.booktok_api_v2.book.Book;
import com.marlisajp.booktok_api_v2.book.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataSeederConfig {
    @Bean
    public CommandLineRunner seedDatabase(AuthorRepository authorRepository, BookRepository bookRepository) {
        return args -> {
            if (authorRepository.count() > 0 || bookRepository.count() > 0) {
                System.out.println("Database already seeded");
                return;
            }

            Author tolkien = Author.builder()
                    .name("J.R.R. Tolkien")
                    .imageUrl("https://cdn.britannica.com/65/66765-050-63A945A7/JRR-Tolkien.jpg?w=300")
                    .biography("English writer, poet, philologist, known for The Hobbit and The Lord of the Rings")
                    .build();

            Author martin = Author.builder()
                    .name("George R.R. Martin")
                    .imageUrl("https://media.wired.com/photos/6793ccb684eed76405feed58/master/w_2240,c_limit/ARS-George-RR-Martin-Physics-Culture-1472022115.png?w=300")
                    .biography("American novelist and short story writer, best known for A Song of Ice and Fire")
                    .build();

            authorRepository.saveAll(Arrays.asList(tolkien, martin));

            Book book1 = Book.builder()
                    .title("The Hobbit")
                    .imageUrl("https://example.com/the-hobbit.jpg")
                    .description("Bilbo Baggins embarks on a thrilling adventure.")
                    .genre("Fantasy")
                    .author(tolkien)
                    .build();

            Book book2 = Book.builder()
                    .title("The Fellowship of the Ring")
                    .imageUrl("https://example.com/fellowship.jpg")
                    .description("The first volume of The Lord of the Rings.")
                    .genre("Fantasy")
                    .author(tolkien)
                    .build();

            Book book3 = Book.builder()
                    .title("A Game of Thrones")
                    .imageUrl("https://example.com/game-of-thrones.jpg")
                    .description("Noble families vie for power in Westeros.")
                    .genre("Fantasy")
                    .author(martin)
                    .build();

            Book book4 = Book.builder()
                    .title("A Clash of Kings")
                    .imageUrl("https://example.com/clash-of-kings.jpg")
                    .description("Westeros is in turmoil with multiple claimants to the throne.")
                    .genre("Fantasy")
                    .author(martin)
                    .build();

            bookRepository.saveAll(Arrays.asList(book1, book2, book3, book4));
            System.out.println("Database seeded with dummy authors and books");
        };
    }
}
