package com.collabera.austinteck.serviceimpl;

import com.collabera.austinteck.model.Book;
import org.apache.commons.validator.routines.ISBNValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.collabera.austinteck.repository.BookRepository;
import com.collabera.austinteck.service.BookService;

import java.util.List;

/**
 * Book ServiceImpl
 * @author : Austin Teck
 */

@Service
public class BookServiceImpl implements BookService {

    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    /**
     * Validate and persist book DTO
     * @param book Book DTO
     * @return Created book
     */
    @Override
    public Book createBook(Book book){
        // ISBN normalization and validation
        String normalizedIsbn = book.getIsbn().replaceAll("[-\\s]", "").toUpperCase();
        ISBNValidator isbnValidator = new ISBNValidator();

        if (!isbnValidator.isValid(normalizedIsbn)){
            log.error("Invalid ISBN format: {}", normalizedIsbn);
            throw new IllegalArgumentException("Invalid ISBN format: {}" + normalizedIsbn);
        }

        // Set normalized ISBN
        book.setIsbn(normalizedIsbn);

        // Validate ISBN, title and author
        if (bookRepository.findByIsbn(book.getIsbn(), book.getTitle(), book.getAuthor())){
            log.error("ISBN: {} already exists with a different name/author", book.getIsbn());
            throw new IllegalArgumentException("ISBN: " + book.getIsbn() + " already exists with a different name/author");
        }

        // Persist
        log.info("Book: {}, Title: {}, Author: {} - successfully created", book.getIsbn(), book.getTitle(), book.getAuthor());
        return bookRepository.save(book);
    }

    /**
     * GET all books
     * @return List of books
     */
    @Override
    public List<Book> getBooks(){
        // GET all books
        return bookRepository.findAll();
    }
}
