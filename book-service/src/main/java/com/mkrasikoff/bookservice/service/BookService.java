package com.mkrasikoff.bookservice.service;

import com.mkrasikoff.bookservice.entity.Book;
import java.util.List;

public interface BookService {

    Book save(Book book, Long userId);

    Book get(Long id);

    void delete(Long id);

    Book update(Long id, Book book);

    List<Book> getAllByUserId(Long userId);

    void deleteAllBooks();
}
