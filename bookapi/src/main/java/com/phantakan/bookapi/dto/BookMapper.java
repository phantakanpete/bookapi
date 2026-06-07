package com.phantakan.bookapi.dto;

import com.phantakan.bookapi.entity.Book;
import com.phantakan.bookapi.utils.DateUtils;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public BookResponse toResponse(Book book) {
        BookResponse response = new BookResponse();

        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setAuthor(book.getAuthor());

        if (book.getPublishedDate() != null) {
            response.setPublishedDate(DateUtils.toBuddhist(book.getPublishedDate()));
        }

        return response;
    }
}
