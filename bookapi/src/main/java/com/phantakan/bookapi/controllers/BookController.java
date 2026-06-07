package com.phantakan.bookapi.controllers;

import com.phantakan.bookapi.dto.ApiResponse;
import com.phantakan.bookapi.dto.BookResponse;
import com.phantakan.bookapi.entity.Book;
import com.phantakan.bookapi.services.BookService;
import com.phantakan.bookapi.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<?>> getBooks(@RequestParam(required = false) String author) {
        // if request has author
        if (author != null) {
            List<BookResponse> books = bookService.findByAuthor(author);
            // if not found any books of the author
            if (books.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(404, "NOT_FOUND", "No books found for author: " + author));
            }
            // else get all books of the author
            return ResponseEntity.ok(new ApiResponse<>(200, "OK", books));
        }
        // else get all books
        return ResponseEntity.ok(new ApiResponse<>(200, "OK", bookService.findAll()));
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<?>> addBooks(@RequestBody List<Book> books) {
        // create errors list
        List<String> errors = new ArrayList<>();

        // loop books
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);

            // validate title
            if (book.getTitle() == null || book.getTitle().isBlank()) {
                errors.add("Book[" + i + "] title is required");
            }

            // validate author
            if (book.getAuthor() == null || book.getAuthor().isBlank()) {
                errors.add("Book[" + i + "] author is required");
            }

            // validate publishedDate
            LocalDate publishedDate = book.getPublishedDate();
            if (publishedDate == null) {
                errors.add("Book[" + i + "] publishedDate is required");
            } else if (!DateUtils.isValidPublishedDate(publishedDate)) {
                errors.add("Book[" + i + "] publishedDate is invalid");
            } else {
                // store Gregorian date in database
                book.setPublishedDate(DateUtils.toGregorian(publishedDate));
            }
        }

        // if found any errors
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, "BAD_REQUEST", errors));
        }

        // else save books to database
        List<Book> saved = bookService.saveAll(books);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(201, "CREATED", saved));
    }
}
