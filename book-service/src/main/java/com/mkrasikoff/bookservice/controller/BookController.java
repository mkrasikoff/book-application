package com.mkrasikoff.bookservice.controller;

import com.mkrasikoff.bookservice.entity.Book;
import com.mkrasikoff.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> save(@RequestBody Book book, @RequestParam Long userId) {
        try {
            Book savedBook = bookService.save(book, userId);
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Conflict: Check your book data.");
            return new ResponseEntity<>(error, HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Book get(@PathVariable Long id) {
        return bookService.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Book update(@PathVariable Long id, @RequestBody Book book) {
        return bookService.update(id, book);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllByUserId(@RequestParam Long userId) {
        List<Book> books = bookService.getAllByUserId(userId);
        return ResponseEntity.ok(books);
    }

    @DeleteMapping
    public void deleteAllBooksByUserId(@RequestParam Long userId) {
        bookService.deleteAllBooksByUserId(userId);
    }
}
