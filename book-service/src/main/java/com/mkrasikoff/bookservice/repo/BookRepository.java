package com.mkrasikoff.bookservice.repo;

import com.mkrasikoff.bookservice.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

}
