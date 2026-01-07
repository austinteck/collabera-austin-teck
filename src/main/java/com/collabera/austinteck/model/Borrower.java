package com.collabera.austinteck.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Borrower DTO
 * @author : Austin Teck
*/

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Borrower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Borrower name cannot be empty")
    private String name;

    @Email
    @NotBlank(message = "Borrower email cannot be empty")
    private String email;
}
