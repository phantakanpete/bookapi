package com.phantakan.bookapi.services;

import com.phantakan.bookapi.dto.BookMapper;
import com.phantakan.bookapi.dto.BookResponse;
import com.phantakan.bookapi.entity.Book;
import com.phantakan.bookapi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceAction implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Autowired
    public BookServiceAction(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public List<Book> saveAll(List<Book> books) {
        return bookRepository.saveAll(books);
    }

    @Override
    public List<BookResponse> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toResponse)
                .toList();
    }

    @Override
    public List<BookResponse> findByAuthor(String author) {
        return bookRepository.findByAuthorStartingWithIgnoreCase(author)
                .stream()
                .map(bookMapper::toResponse)
                .toList();
    }
}
