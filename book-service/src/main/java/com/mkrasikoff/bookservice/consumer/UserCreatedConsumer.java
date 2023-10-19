package com.mkrasikoff.bookservice.consumer;

import com.mkrasikoff.bookservice.entity.Book;
import com.mkrasikoff.bookservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserCreatedConsumer {

    private final BookService bookService;

    @Autowired
    public UserCreatedConsumer(BookService bookService) {
        this.bookService = bookService;
    }

    @KafkaListener(topics = "user-created-topic")
    public void handleUserCreated(Long userId) {
        for (int i = 0; i < 3; i++) {
            Book book = new Book();
            book.setTitle("Default Book " + (i + 1));
            book.setUserId(userId);
            bookService.save(book, userId);
        }
    }
}
