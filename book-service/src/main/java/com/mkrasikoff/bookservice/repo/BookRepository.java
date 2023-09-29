package com.mkrasikoff.bookservice.repo;

import com.mkrasikoff.bookservice.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * The BookRepository interface provides CRUD (Create, Read, Update, Delete)
 * functionalities for the Book entity by extending the CrudRepository
 * interface provided by Spring Data JPA.
 *
 * By extending CrudRepository, it leverages default database operations
 * such as save, delete, findById, and many others without the need for custom
 * implementations.
 *
 * Use this repository interface to perform database operations related to the
 * Book entity.
 */
@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findAll();
}
