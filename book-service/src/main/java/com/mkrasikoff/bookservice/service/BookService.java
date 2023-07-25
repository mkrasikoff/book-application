package com.mkrasikoff.bookservice.service;

import com.mkrasikoff.bookservice.entity.Book;

public interface BookService {

    Book save(Long authorId, String text);

    Book get(Long id);

    void delete(Long id);

    Book update(Long id, String text);
}
