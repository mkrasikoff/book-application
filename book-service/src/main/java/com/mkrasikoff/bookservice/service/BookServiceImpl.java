package com.mkrasikoff.bookservice.service;

import com.mkrasikoff.bookservice.entity.Book;
import com.mkrasikoff.bookservice.repo.BookRepository;
import lombok.RequiredArgsConstructor;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import java.net.URI;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private static final Logger LOGGER = LogManager.getLogger(BookServiceImpl.class);

    @Value("${api.user.url}")
    private String userServiceUrl;

    @Override
    public Book save(Long authorId, String text) {
        Book book = new Book();
        book.setAuthorId(authorId);
        book.setText(text);
        book.setPostedAt(LocalDate.now());

        Book updated = bookRepository.save(book);
        updateUser(authorId);

        return updated;
    }

    private void updateUser(Long authorId) {
        String address = userServiceUrl + authorId + "/posts";
        URI uri = URI.create(address);

        CloseableHttpClient client = HttpClients.createDefault();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));

        try {
            restTemplate.patchForObject(uri, null, Object.class);
        }
        catch (HttpClientErrorException.NotFound exception) {
            LOGGER.warn("New post was created, but user with this authorId was not found.");
        }
    }

    @Override
    public Book get(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void delete(Long id) {
        try {
            Book book = bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            bookRepository.deleteById(id);

            updateUser(book.getAuthorId());
        }
        catch (EmptyResultDataAccessException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Book update(Long id, String text) {
        Book oldBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Book updatedBook = new Book();
        updatedBook.setId(id);
        updatedBook.setText(text);
        updatedBook.setAuthorId(oldBook.getAuthorId());
        updatedBook.setPostedAt(LocalDate.now());

        return bookRepository.save(updatedBook);
    }
}
