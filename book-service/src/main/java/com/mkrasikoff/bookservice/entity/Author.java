package com.mkrasikoff.bookservice.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(min = 2, max = 50, message = "Author name must be between 2 and 50 characters.")
    private String name;

    @Column(nullable = false)
    @Size(min = 2, max = 50, message = "Author surname must be between 2 and 50 characters.")
    private String surname;
}
