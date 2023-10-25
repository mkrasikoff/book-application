package com.mkrasikoff.bookservice.service;

import com.mkrasikoff.bookservice.entity.Author;

public interface AuthorService {

    Author save(Author author);

    Author get(Long id);

    void delete(Long id);

    Author update(Long id, Author author);
}
