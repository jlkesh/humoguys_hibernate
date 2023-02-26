package dev.jlkesh;

import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorServiceTest {

    AuthorService service;

    @BeforeEach
    void setUp() {
        service = new AuthorService();
    }

    @Test
    void save() {
        Author author = Author.builder()
                .fullName("Shahzod Aka Muhammadov")
                .age(29)
                .build();
        service.save(author);
    }

    @Test
    void get() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
        Author author = service.get("aba12702-2edf-423a-a483-fed7fcd4422d");
        service.delete(author);
    }

    @Test
    void deleteById() {
    }
}