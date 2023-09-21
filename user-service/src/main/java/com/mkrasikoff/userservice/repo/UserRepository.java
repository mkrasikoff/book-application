package com.mkrasikoff.userservice.repo;

import com.mkrasikoff.userservice.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * The UserRepository interface provides CRUD (Create, Read, Update, Delete)
 * functionalities for the User entity by extending the CrudRepository
 * interface provided by Spring Data JPA.
 *
 * By extending CrudRepository, it leverages default database operations
 * such as save, delete, findById, and many others without the need for custom
 * implementations.
 *
 * Use this repository interface to perform database operations related to the
 * User entity.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Finds the active user in the database.
     *
     * This method will return an Optional containing the User entity that has
     * its 'isActive' field set to true. If no active user is found,
     * it returns an empty Optional.
     *
     * @return an Optional containing the active User or an empty Optional if not found.
     */
    Optional<User> findByIsActiveTrue();
}
