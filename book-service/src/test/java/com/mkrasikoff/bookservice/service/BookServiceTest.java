package com.mkrasikoff.bookservice.service;

import com.mkrasikoff.bookservice.entity.Book;
import com.mkrasikoff.bookservice.repo.AuthorRepository;
import com.mkrasikoff.bookservice.repo.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BookServiceTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_whenCorrectBookIsGiven_thenBookWasSaved() {
        Book book = new Book();
        when(bookRepository.save(any())).thenReturn(book);

        Book result = bookService.save(book, 1L);

        assertEquals(book, result);
    }

    @Test
    void update_whenNewDataAboutBookIsGiven_thenBookWasUpdated() {
        Book book = new Book();
        Book newBook = new Book();
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));
        when(bookRepository.save(any())).thenReturn(book);

        Book result = bookService.update(1L, newBook);

        assertEquals(book, result);
    }

    @Test
    void update_whenBookDoesNotExist_thenExceptionWasThrown() {
        when(bookRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> bookService.update(1L, new Book()));
    }

    @Test
    void get_whenBookExists_thenBookWasReturned() {
        Book book = new Book();
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));

        Book result = bookService.get(1L);

        assertEquals(book, result);
    }

    @Test
    void get_whenBookDoesNotExist_thenExceptionWasThrown() {
        when(bookRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> bookService.get(1L));
    }

    @Test
    void delete_whenBookExists_thenBookWasDeleted() {
        when(bookRepository.existsById(any())).thenReturn(true);

        bookService.delete(1L);

        verify(bookRepository).deleteById(1L);
    }

    @Test
    void delete_whenBookDoesExist_thenExceptionWasThrown() {
        when(bookRepository.existsById(any())).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> bookService.delete(1L));
    }
}
