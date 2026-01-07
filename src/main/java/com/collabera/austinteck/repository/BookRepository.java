package com.collabera.austinteck.repository;

import com.collabera.austinteck.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Book Repository
 * @author : Austin Teck
*/

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT COUNT(b) > 0 FROM Book b " +
            "WHERE b.isbn = :isbn " +
            "AND (LOWER(b.title) <> LOWER(:title) " +
            "     OR LOWER(b.author) <> LOWER(:author))")
    boolean findByIsbn(
            @Param("isbn") String isbn,
            @Param("title") String title,
            @Param("author") String author
    );
}
