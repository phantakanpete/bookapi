package com.phantakan.bookapi.services;

import com.phantakan.bookapi.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> saveAll(List<Book> books);
    List<Book> findAll();
    List<Book> findByAuthor(String author);
}
