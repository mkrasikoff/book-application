package com.mkrasikoff.bookservice.service;

import com.mkrasikoff.bookservice.entity.Author;
import java.util.Optional;

public interface AuthorService {

    Author save(Author author);

    Author get(Long id);

    Optional<Author> getByNameAndSurname(String name, String surname);

    void delete(Long id);

    Author update(Long id, Author author);
}
