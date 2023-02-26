package dev.jlkesh;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    BookService service;
    AuthorService authorService;

    @BeforeEach
    void setUp() {
        service = new BookService();
        authorService = new AuthorService();
    }

    @AfterEach
    void tearDown() {
        System.out.println(":::::::::::::::::::::::: Test Completed ::::::::::::::::::::::::");
    }

    @Test
    void save() {
        Author author = Author.builder()
                .fullName("Shahzod Aka Muhammadov")
                .age(29)
                .build();
        /*authorService.save(author);*/
        Book book = Book.builder()
                .title("Spring Framework Form Beginners")
                .author(author)
                .build();
        service.save(book);
    }

    @Test
    void get() {
        Book book = service.get("e3fde88c-d285-44e0-8830-1b6824d3305a");
        System.out.println("Hello :::::::::::::::::::::::::;");
        Author author = book.getAuthor();
        System.out.println(author);
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void deleteById() {
    }
}