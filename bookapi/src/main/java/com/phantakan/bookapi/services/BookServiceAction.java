package com.phantakan.bookapi.services;

import com.phantakan.bookapi.entity.Book;
import com.phantakan.bookapi.repository.BookRepository;
import com.phantakan.bookapi.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceAction implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceAction(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> saveAll(List<Book> books) {
        return bookRepository.saveAll(books);
    }

    @Override
    public List<Book> findAll() {
        return convertToBuddhist(bookRepository.findAll());
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return convertToBuddhist(bookRepository.findByAuthorStartingWithIgnoreCase(author));
    }

    private List<Book> convertToBuddhist(List<Book> books) {
        return books.stream().map(book -> {
                    if (book.getPublishedDate() != null) {
                        book.setPublishedDate(DateUtils.toBuddhist(book.getPublishedDate()));
                    }
                    return book;
                }).toList();
    }
}
