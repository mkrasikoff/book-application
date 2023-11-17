package com.mkrasikoff.bookservice.service;

import com.mkrasikoff.bookservice.entity.Author;
import com.mkrasikoff.bookservice.entity.Book;
import com.mkrasikoff.bookservice.repo.AuthorRepository;
import com.mkrasikoff.bookservice.repo.BookRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    @Override
    public Book save(Book book, Long userId) {
        book.setUserId(userId);
        setAuthorForBook(book);
        return bookRepository.save(book);
    }

    @Override
    public Book update(Long id, Book newBook) {
        return bookRepository.findById(id).map(book -> {
            setAuthorForBook(newBook);
            book.setTitle(newBook.getTitle());
            book.setDescription(newBook.getDescription());
            book.setRating(newBook.getRating());
            book.setImageUrl(newBook.getImageUrl());
            return bookRepository.save(book);
        }).orElseThrow(() -> {
            log.error("Failed to update book with ID '{}': Not Found", id);
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        });
    }

    private void setAuthorForBook(Book book) {
        if (book.getAuthor() != null) {
            Author author = book.getAuthor();
            String name = author.getName();
            String surname = author.getSurname();

            Author existingAuthor = authorRepository.findByNameAndSurname(name, surname).orElse(null);

            if (existingAuthor == null) {
                Author newAuthor = new Author();
                newAuthor.setName(name);
                newAuthor.setSurname(surname);

                existingAuthor = authorRepository.save(newAuthor);
            }

            book.setAuthor(existingAuthor);
        }
    }

    @Override
    public Book get(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> {
                    log.error("Failed to fetch book with ID '{}': Not Found", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
                });
    }

    @Override
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            log.error("Failed to delete book with ID '{}': Not Found", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
        bookRepository.deleteById(id);
    }

    @Override
    public List<Book> getAllByUserId(Long userId) {
        return bookRepository.findByUserId(userId);
    }

    @Override
    public void deleteAllBooksByUserId(Long userId) {
        List<Book> booksToDelete = bookRepository.findByUserId(userId);
        for (Book book : booksToDelete) {
            try {
                bookRepository.delete(book);
            } catch (EmptyResultDataAccessException e) {
                log.error("Failed to delete book during bulk delete operation: Book with ID '{}' Not Found", book.getId(), e);
            }
        }
    }
}
