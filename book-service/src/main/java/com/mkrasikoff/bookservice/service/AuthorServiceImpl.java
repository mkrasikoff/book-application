package com.mkrasikoff.bookservice.service;

import com.mkrasikoff.bookservice.entity.Author;
import com.mkrasikoff.bookservice.repo.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private static final Logger log = LoggerFactory.getLogger(AuthorServiceImpl.class);

    @Override
    public Author save(Author author) {
        Optional<Author> existingAuthor = authorRepository.findByNameAndSurname(author.getName(), author.getSurname());
        if (existingAuthor.isPresent()) {
            log.info("Attempt to save duplicate author: '{}' '{}'", author.getName(), author.getSurname());
            return existingAuthor.get();
        }
        return authorRepository.save(author);
    }

    @Override
    public Author get(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> {
                    log.error("Author with ID '{}' not found", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found");
                });
    }

    @Override
    public Optional<Author> getByNameAndSurname(String name, String surname) {
        Optional<Author> authorOptional = authorRepository.findByNameAndSurname(name, surname);
        if (!authorOptional.isPresent()) {
            log.info("Author with name: '{}' and surname: '{}' not found", name, surname);
        }
        return authorOptional;
    }

    @Override
    public void delete(Long id) {
        if (!authorRepository.existsById(id)) {
            log.error("Attempt to delete non-existent author with ID '{}'", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found");
        }
        authorRepository.deleteById(id);
    }

    @Override
    public Author update(Long id, Author newAuthor) {
        return authorRepository.findById(id).map(author -> {
            author.setName(newAuthor.getName());
            author.setSurname(newAuthor.getSurname());
            return authorRepository.save(author);
        }).orElseThrow(() -> {
            log.error("Attempt to update non-existent author with ID '{}'", id);
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found");
        });
    }
}
