package com.collabera.austinteck.repository;

import com.collabera.austinteck.model.Book;
import com.collabera.austinteck.model.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Borrowing Repository
 * @author : Austin Teck
*/

public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {

    boolean existsByBookAndReturnDateIsNull(Book book);

    Optional<Borrowing> findByBookAndReturnDateIsNull(Book book);
}
