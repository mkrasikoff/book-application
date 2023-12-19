package com.mkrasikoff.bookservice.service;

import com.mkrasikoff.bookservice.entity.Author;
import com.mkrasikoff.bookservice.entity.Book;
import com.mkrasikoff.bookservice.entity.UniqueBook;
import com.mkrasikoff.bookservice.repo.AuthorRepository;
import com.mkrasikoff.bookservice.repo.BookRepository;
import com.mkrasikoff.bookservice.repo.UniqueBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.server.ResponseStatusException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BookServiceTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private UniqueBookRepository uniqueBookRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_whenCorrectBookIsGiven_thenBookWasSaved() {
        Book book = new Book();
        book.setRating(5.0);
        UniqueBook uniqueBook = new UniqueBook();
        uniqueBook.setAverageRating(4.0);
        uniqueBook.setRatingCount(1);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(uniqueBookRepository.findByTitleAndAuthor(anyString(), any(Author.class))).thenReturn(Optional.of(uniqueBook));
        when(uniqueBookRepository.save(any(UniqueBook.class))).thenReturn(uniqueBook);

        Book result = bookService.save(book, 1L);

        verify(bookRepository).save(any(Book.class));
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

    @Test
    void save_whenNewAuthor_thenAuthorIsSaved() {
        Author newAuthor = new Author();
        newAuthor.setName("New Author Name");
        newAuthor.setSurname("New Author Surname");
        Book book = new Book();
        book.setTitle("New Book Title");
        book.setAuthor(newAuthor);
        book.setRating(5.0);
        UniqueBook uniqueBook = new UniqueBook();
        uniqueBook.setAuthor(newAuthor);
        uniqueBook.setAverageRating(4.0);
        uniqueBook.setRatingCount(1);

        when(uniqueBookRepository.findByTitleAndAuthor(book.getTitle(), book.getAuthor())).thenReturn(Optional.empty());
        when(uniqueBookRepository.save(any(UniqueBook.class))).thenReturn(uniqueBook);  // Return the uniqueBook for consistency
        when(authorRepository.findByNameAndSurname(newAuthor.getName(), newAuthor.getSurname())).thenReturn(Optional.empty());
        when(authorRepository.save(any(Author.class))).thenReturn(newAuthor);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book result = bookService.save(book, 1L);

        verify(bookRepository).save(book);
        verify(authorRepository).save(newAuthor);
        assertEquals(book, result);
    }

    @Test
    void save_whenExistingAuthor_thenAuthorIsNotSavedAgain() {
        Author author = new Author();
        author.setName("Existing Author Name");
        author.setSurname("Existing Author Surname");
        Book book = new Book();
        book.setAuthor(author);
        book.setTitle("Book Title");
        book.setRating(4.0);
        UniqueBook uniqueBook = new UniqueBook();
        uniqueBook.setTitle("Book Title");
        uniqueBook.setAuthor(author);
        uniqueBook.setAverageRating(4.0);
        uniqueBook.setRatingCount(1);

        when(authorRepository.findByNameAndSurname(any(), any())).thenReturn(Optional.of(author));
        when(bookRepository.save(any())).thenReturn(book);
        when(uniqueBookRepository.findByTitleAndAuthor(book.getTitle(), book.getAuthor()))
                .thenReturn(Optional.of(uniqueBook));

        Book result = bookService.save(book, 1L);

        verify(authorRepository, never()).save(any(Author.class));
        verify(uniqueBookRepository).save(uniqueBook);
        assertEquals(book, result);
        assertEquals(4.0, uniqueBook.getAverageRating());
        assertEquals(2, uniqueBook.getRatingCount());
    }

    @Test
    void deleteAllBooksByUserId_whenUserHasBooks_thenAllBooksAreDeleted() {
        List<Book> books = Arrays.asList(new Book(), new Book());
        when(bookRepository.findByUserId(any())).thenReturn(books);

        bookService.deleteAllBooksByUserId(1L);

        verify(bookRepository, times(books.size())).delete(any(Book.class));
    }

    @Test
    void deleteAllBooksByUserId_whenDeletionThrowsException_thenOtherBooksAreStillDeleted() {
        List<Book> books = Arrays.asList(new Book(), new Book());
        int booksCount = books.size();
        when(bookRepository.findByUserId(any())).thenReturn(books);
        doThrow(new EmptyResultDataAccessException(1)).when(bookRepository).delete(books.get(0));

        bookService.deleteAllBooksByUserId(1L);

        verify(bookRepository, times(booksCount)).delete(any(Book.class));
    }

    @Test
    void getAllBooks_whenBooksExist_thenAllBooksAreReturned() {
        List<Book> expectedBooks = Arrays.asList(new Book(), new Book());
        when(bookRepository.findAll()).thenReturn(expectedBooks);

        List<Book> result = bookService.getAllBooks();

        assertEquals(expectedBooks, result);
        verify(bookRepository).findAll();
    }

    @Test
    void getAllBooks_whenNoBooksExist_thenReturnsEmptyList() {
        when(bookRepository.findAll()).thenReturn(Collections.emptyList());

        List<Book> result = bookService.getAllBooks();

        assertTrue(result.isEmpty());
        verify(bookRepository).findAll();
    }

    @Test
    void getAllByUserId_whenNoBooks_thenReturnsEmptyList() {
        when(bookRepository.findByUserId(any())).thenReturn(Collections.emptyList());

        List<Book> result = bookService.getAllByUserId(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    void getAllByUserId_whenMultipleBooks_thenReturnsAllBooks() {
        List<Book> expectedBooks = Arrays.asList(new Book(), new Book());
        when(bookRepository.findByUserId(any())).thenReturn(expectedBooks);

        List<Book> result = bookService.getAllByUserId(1L);

        assertEquals(expectedBooks.size(), result.size());
    }
}
