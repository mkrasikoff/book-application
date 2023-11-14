package com.mkrasikoff.bookservice.consumer;

import com.mkrasikoff.bookservice.entity.Author;
import com.mkrasikoff.bookservice.entity.Book;
import com.mkrasikoff.bookservice.service.AuthorService;
import com.mkrasikoff.bookservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserCreatedConsumer {

    private final BookService bookService;
    private final AuthorService authorService;
    private final Map<Integer, BookInfo> bookDefaults;
    public static final String DEFAULT_AUTHOR_NAME = "Fyodor";
    public static final String DEFAULT_AUTHOR_SURNAME = "Dostoevsky";

    @Autowired
    public UserCreatedConsumer(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;

        this.bookDefaults = new HashMap<>();
        bookDefaults.put(1, new BookInfo(
                "Demons",
                "Inspired by the true story of a political murder that horrified Russians in 1869, Fyodor Dostoevsky conceived of Demons as a \"novel-pamphlet\" in which he would say everything about the plague of materialist ideology that he saw infecting his native land. What emerged was a prophetic and ferociously funny masterpiece of ideology and murder in pre-revolutionary Russia.",
                "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1524586008i/5695.jpg"
        ));
        bookDefaults.put(2, new BookInfo(
                "The Idiot",
                "Returning to Russia from a sanitarium in Switzerland, the Christ-like epileptic Prince Myshkin finds himself enmeshed in a tangle of love, torn between two women—the notorious kept woman Nastasya and the pure Aglaia—both involved, in turn, with the corrupt, money-hungry Ganya. In the end, Myshkin’s honesty, goodness, and integrity are shown to be unequal to the moral emptiness of those around him.",
                "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1657539107i/12505.jpg"
        ));
        bookDefaults.put(3, new BookInfo(
                "The Brothers Karamazov",
                "The Brothers Karamazov is a murder mystery, a courtroom drama, and an exploration of erotic rivalry in a series of triangular love affairs involving the “wicked and sentimental” Fyodor Pavlovich Karamazov and his three sons―the impulsive and sensual Dmitri; the coldly rational Ivan; and the healthy, red-cheeked young novice Alyosha. Through the gripping events of their story, Dostoevsky portrays the whole of Russian life, is social and spiritual striving, in what was both the golden age and a tragic turning point in Russian culture.",
                "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1427728126i/4934.jpg"
        ));
    }

    @KafkaListener(topics = "user-created-topic")
    public void handleUserCreated(Long userId) {
        Author dostoevsky = ensureDefaultAuthorExists();

        for (int i = 1; i <= 3; i++) {
            Book book = new Book();
            BookInfo bookInfo = bookDefaults.get(i);

            book.setTitle(bookInfo.title);
            book.setDescription(bookInfo.description);
            book.setUserId(userId);
            book.setRating(5.0);
            book.setAuthor(dostoevsky);
            book.setImageUrl(bookInfo.imageUrl);

            bookService.save(book, userId);
        }
    }

    private Author ensureDefaultAuthorExists() {
        Optional<Author> existingAuthor = authorService.getByNameAndSurname(DEFAULT_AUTHOR_NAME, DEFAULT_AUTHOR_SURNAME);

        return existingAuthor.orElseGet(() -> {
            Author author = new Author();
            author.setName(DEFAULT_AUTHOR_NAME);
            author.setSurname(DEFAULT_AUTHOR_SURNAME);
            return authorService.save(author);
        });
    }

    private static class BookInfo {
        String title;
        String description;
        String imageUrl;

        BookInfo(String title, String description, String imageUrl) {
            this.title = title;
            this.description = description;
            this.imageUrl = imageUrl;
        }
    }
}
