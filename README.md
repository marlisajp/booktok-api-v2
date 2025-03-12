Run dev profile
```
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```
### API Routes
- `/api/books/all`: Returns all books with author data
- `/api/books/{id}`: Returns one book with author data
- `/api/authors/all`: Returns all authors
- `/api/authors/{id}`: Returns one author with all books data

todos
- add friend logic