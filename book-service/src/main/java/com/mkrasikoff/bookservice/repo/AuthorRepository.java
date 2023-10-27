package com.mkrasikoff.bookservice.repo;

import com.mkrasikoff.bookservice.entity.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

    Optional<Author> findByNameAndSurname(String name, String surname);
}
