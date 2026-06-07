package com.phantakan.bookapi.services;

import com.phantakan.bookapi.dto.BookResponse;
import com.phantakan.bookapi.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> saveAll(List<Book> books);
    List<BookResponse> findAll();
    List<BookResponse> findByAuthor(String author);
}
