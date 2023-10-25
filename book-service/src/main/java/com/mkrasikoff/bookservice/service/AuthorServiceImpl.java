package com.mkrasikoff.bookservice.service;

import com.mkrasikoff.bookservice.entity.Author;
import com.mkrasikoff.bookservice.repo.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author get(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));
    }

    @Override
    public void delete(Long id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found");
        }
    }

    @Override
    public Author update(Long id, Author newAuthor) {
        return authorRepository.findById(id).map(author -> {
            author.setName(newAuthor.getName());
            author.setSurname(newAuthor.getSurname());
            return authorRepository.save(author);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));
    }
}
