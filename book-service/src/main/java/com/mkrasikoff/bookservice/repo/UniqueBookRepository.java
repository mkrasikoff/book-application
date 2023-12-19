package com.mkrasikoff.bookservice.repo;

import com.mkrasikoff.bookservice.entity.Author;
import com.mkrasikoff.bookservice.entity.UniqueBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UniqueBookRepository extends JpaRepository<UniqueBook, Long> {
    Optional<UniqueBook> findByTitleAndAuthor(String title, Author author);
}
