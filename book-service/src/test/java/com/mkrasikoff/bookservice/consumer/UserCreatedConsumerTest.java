package com.mkrasikoff.bookservice.consumer;

import com.mkrasikoff.bookservice.entity.Author;
import com.mkrasikoff.bookservice.entity.Book;
import com.mkrasikoff.bookservice.service.AuthorService;
import com.mkrasikoff.bookservice.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserCreatedConsumerTest {

    @InjectMocks
    private UserCreatedConsumer userCreatedConsumer;

    @Mock
    private BookService bookService;

    @Mock
    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleUserCreated_whenUserCreated_thenDefaultBooksAreCreated() {
        Long userId = 1L;
        Author author = new Author();
        author.setName(UserCreatedConsumer.DEFAULT_AUTHOR_NAME);
        author.setSurname(UserCreatedConsumer.DEFAULT_AUTHOR_SURNAME);
        Book book = new Book();
        book.setAuthor(author);
        when(authorService.getByNameAndSurname(anyString(), anyString())).thenReturn(Optional.of(author));
        when(bookService.save(any(Book.class), eq(userId))).thenReturn(book);

        userCreatedConsumer.handleUserCreated(userId);

        verify(authorService).getByNameAndSurname(UserCreatedConsumer.DEFAULT_AUTHOR_NAME, UserCreatedConsumer.DEFAULT_AUTHOR_SURNAME);
        verify(bookService, times(3)).save(any(Book.class), eq(userId));
    }

    @Test
    void handleUserCreated_whenAuthorExists_thenDefaultBooksAreCreatedWithExistingAuthor() {
        Long userId = 1L;
        Author existingAuthor = new Author();
        existingAuthor.setName(UserCreatedConsumer.DEFAULT_AUTHOR_NAME);
        existingAuthor.setSurname(UserCreatedConsumer.DEFAULT_AUTHOR_SURNAME);
        when(authorService.getByNameAndSurname(UserCreatedConsumer.DEFAULT_AUTHOR_NAME, UserCreatedConsumer.DEFAULT_AUTHOR_SURNAME))
                .thenReturn(Optional.of(existingAuthor));

        userCreatedConsumer.handleUserCreated(userId);

        verify(authorService, never()).save(any(Author.class));
        verify(bookService, times(3)).save(any(Book.class), eq(userId));
    }

    @Test
    void handleUserCreated_whenAuthorDoesNotExist_thenDefaultBooksAreCreatedWithNewAuthor() {
        Long userId = 1L;
        Author newAuthor = new Author();
        newAuthor.setName(UserCreatedConsumer.DEFAULT_AUTHOR_NAME);
        newAuthor.setSurname(UserCreatedConsumer.DEFAULT_AUTHOR_SURNAME);
        when(authorService.getByNameAndSurname(UserCreatedConsumer.DEFAULT_AUTHOR_NAME, UserCreatedConsumer.DEFAULT_AUTHOR_SURNAME))
                .thenReturn(Optional.empty());
        when(authorService.save(any(Author.class))).thenReturn(newAuthor);

        userCreatedConsumer.handleUserCreated(userId);

        verify(authorService).save(any(Author.class));
        verify(bookService, times(3)).save(any(Book.class), eq(userId));
    }
}
