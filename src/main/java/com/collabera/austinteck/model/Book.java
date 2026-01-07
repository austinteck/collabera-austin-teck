package com.collabera.austinteck.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Book DTO
 * @author : Austin Teck
*/

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "ISBN cannot be empty")
    private String isbn;

    @NotBlank(message = "Book title cannot be empty")
    private String title;

    @NotBlank(message = "Book author cannot be empty")
    private String author;
}
