package com.mkrasikoff.bookservice.controller;

import com.mkrasikoff.bookservice.dto.SaveDTO;
import com.mkrasikoff.bookservice.dto.UpdateDTO;
import com.mkrasikoff.bookservice.entity.Book;
import com.mkrasikoff.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Book save(@RequestBody SaveDTO saveDTO) {
        Long authorId = saveDTO.getAuthorId();
        String text = saveDTO.getText();
        return bookService.save(authorId, text);
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
    public Book update(@PathVariable Long id, @RequestBody UpdateDTO updateDTO) {
        String text = updateDTO.getText();
        return bookService.update(id, text);
    }
}
