package com.phantakan.bookapi.repository;

import com.phantakan.bookapi.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthorStartingWithIgnoreCase(String author);
}
