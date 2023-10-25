package com.mkrasikoff.bookservice.service;

import com.mkrasikoff.bookservice.entity.Author;
import com.mkrasikoff.bookservice.entity.Book;
import com.mkrasikoff.bookservice.repo.AuthorRepository;
import com.mkrasikoff.bookservice.repo.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public Book save(Book book, Long userId) {
        book.setUserId(userId);

        // Temporary - Ensure the author exists before saving the book
        if (book.getAuthor() != null && book.getAuthor().getId() != null) {
            Author author = authorRepository.findById(book.getAuthor().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));
            book.setAuthor(author);
        }

        return bookRepository.save(book);
    }

    @Override
    public Book get(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void delete(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Book update(Long id, Book newBook) {
        return bookRepository.findById(id).map(book -> {
            book.setTitle(newBook.getTitle());

            // Temporary - Set the author if it exists in the newBook
            if (newBook.getAuthor() != null && newBook.getAuthor().getId() != null) {
                Author author = authorRepository.findById(newBook.getAuthor().getId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));
                book.setAuthor(author);
            }

            book.setDescription(newBook.getDescription());
            book.setRating(newBook.getRating());
            book.setImageUrl(newBook.getImageUrl());
            return bookRepository.save(book);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Book> getAllByUserId(Long userId) {
        return bookRepository.findByUserId(userId);
    }

    @Override
    public void deleteAllBooksByUserId(Long userId) {
        List<Book> booksToDelete = bookRepository.findByUserId(userId);
        for(Book book : booksToDelete) {
            try {
                bookRepository.delete(book);
            } catch(EmptyResultDataAccessException e) {
                // Log the error but continue deleting the other books
                // log.warn("Attempted to delete book with ID {} but it was not found.", book.getId());
            }
        }
    }
}
