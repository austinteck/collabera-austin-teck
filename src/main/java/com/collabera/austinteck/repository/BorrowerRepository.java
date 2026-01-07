package com.collabera.austinteck.repository;

import com.collabera.austinteck.model.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Borrower Repository
 * @author : Austin Teck
*/

public interface BorrowerRepository extends JpaRepository<Borrower, Long> {

    Optional<Borrower> findByEmail(String email);
}
