package com.mkrasikoff.bookservice.service;

import com.mkrasikoff.bookservice.entity.Author;
import com.mkrasikoff.bookservice.repo.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthorServiceTest {

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Mock
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_whenNewAuthor_thenAuthorIsSaved() {
        Author author = new Author();
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        Author savedAuthor = authorService.save(author);

        assertNotNull(savedAuthor);
        verify(authorRepository).save(author);
    }

    @Test
    void save_whenAuthorWithSameNameAndSurnameExists_thenNoNewAuthorIsCreated() {
        Author author = new Author();
        author.setName("Existing");
        author.setSurname("Author");
        when(authorRepository.findByNameAndSurname("Existing", "Author")).thenReturn(Optional.of(author));

        Author savedAuthor = authorService.save(author);

        verify(authorRepository, never()).save(any(Author.class));
        assertNotNull(savedAuthor);
    }

    @Test
    void get_whenAuthorExists_thenAuthorIsReturned() {
        Long authorId = 1L;
        Author author = new Author();
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));

        Author foundAuthor = authorService.get(authorId);

        assertNotNull(foundAuthor);
        assertEquals(author, foundAuthor);
    }

    @Test
    void get_whenAuthorDoesNotExist_thenResponseStatusExceptionIsThrown() {
        Long authorId = 1L;
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> authorService.get(authorId));
    }

    @Test
    void getByNameAndSurname_whenAuthorExists_thenOptionalOfAuthorIsReturned() {
        String name = "John";
        String surname = "Doe";
        Author author = new Author();
        when(authorRepository.findByNameAndSurname(name, surname)).thenReturn(Optional.of(author));

        Optional<Author> foundAuthor = authorService.getByNameAndSurname(name, surname);

        assertTrue(foundAuthor.isPresent());
        assertEquals(author, foundAuthor.get());
    }

    @Test
    void getByNameAndSurname_whenAuthorDoesNotExist_thenEmptyOptionalIsReturned() {
        String name = "John";
        String surname = "Doe";
        when(authorRepository.findByNameAndSurname(name, surname)).thenReturn(Optional.empty());

        Optional<Author> foundAuthor = authorService.getByNameAndSurname(name, surname);

        assertFalse(foundAuthor.isPresent());
    }

    @Test
    void delete_whenAuthorExists_thenAuthorIsDeleted() {
        Long authorId = 1L;
        when(authorRepository.existsById(authorId)).thenReturn(true);

        authorService.delete(authorId);

        verify(authorRepository).deleteById(authorId);
    }

    @Test
    void delete_whenAuthorDoesNotExist_thenResponseStatusExceptionIsThrown() {
        Long authorId = 1L;
        when(authorRepository.existsById(authorId)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> authorService.delete(authorId));
    }

    @Test
    void update_whenAuthorExists_thenAuthorIsUpdated() {
        Long authorId = 1L;
        Author newAuthor = new Author();
        newAuthor.setName("New Name");
        newAuthor.setSurname("New Surname");

        Author existingAuthor = new Author();
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(existingAuthor));
        when(authorRepository.save(any(Author.class))).thenReturn(existingAuthor);

        Author updatedAuthor = authorService.update(authorId, newAuthor);

        assertNotNull(updatedAuthor);
        assertEquals(newAuthor.getName(), updatedAuthor.getName());
        assertEquals(newAuthor.getSurname(), updatedAuthor.getSurname());
    }

    @Test
    void update_whenAuthorDoesNotExist_thenResponseStatusExceptionIsThrown() {
        Long authorId = 1L;
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> authorService.update(authorId, new Author()));
    }

    @Test
    void update_whenAuthorIdIsInvalid_thenResponseStatusExceptionIsThrown() {
        Long invalidAuthorId = 999L;
        when(authorRepository.findById(invalidAuthorId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> authorService.update(invalidAuthorId, new Author()));
    }
}
