package com.collabera.austinteck.controller;

import com.collabera.austinteck.model.request.BorrowingRequest;
import com.collabera.austinteck.model.response.BorrowingResponse;
import com.collabera.austinteck.service.BorrowingService;
import jakarta.validation.Valid;
import com.collabera.austinteck.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.collabera.austinteck.service.BookService;

import java.util.List;

/**
 * Book Controller
 * @author : Austin Teck
*/

@RestController
@RequestMapping("/api/books")
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;
    private final BorrowingService borrowingService;

    @Autowired
    public BookController(BookService bookService, BorrowingService borrowingService) {
        this.bookService = bookService;
        this.borrowingService = borrowingService;
    }

    /**
     * CREATE new book (/api/books/create)
     * @param book Book DTO
     * @return Created book
     */
    @PostMapping("/create")
    public Book createBook(@Valid @RequestBody Book book){
        log.info("Creating book: {} by {}", book.getTitle(), book.getAuthor());
        return bookService.createBook(book);
    }

    /**
     * GET all books (/api/books/getBooks)
     * @return List of Book DTOs
     */
    @GetMapping("/getBooks")
    public List<Book> getBooks(){
        log.info("Getting all books...");
        return bookService.getBooks();
    }

    /**
     * Borrow book
     * @param borrowingRequest BorrowingRequest DTO
     * @return ResponseEntity<BorrowingResponse> JSON message
     */
    @PostMapping("/borrow")
    public ResponseEntity<BorrowingResponse> borrowBook(@Valid @RequestBody BorrowingRequest borrowingRequest){
        log.info("Borrowing - book ID: {}, borrower ID: {}", borrowingRequest.bookId(), borrowingRequest.borrowerId());
        BorrowingResponse res = borrowingService.borrowBook(borrowingRequest);

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    /**
     * Return book
     * @param borrowingRequest BorrowingRequest DTO
     * @return ResponseEntity<BorrowingResponse> JSON message
     */
    @PostMapping("/return")
    public ResponseEntity<BorrowingResponse> returnBook(@Valid @RequestBody BorrowingRequest borrowingRequest){
        log.info("Returning - book ID: {}", borrowingRequest.bookId());
        BorrowingResponse res = borrowingService.returnBook(borrowingRequest);

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
