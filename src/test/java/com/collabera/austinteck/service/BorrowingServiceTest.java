package com.collabera.austinteck.service;

import com.collabera.austinteck.model.Book;
import com.collabera.austinteck.model.Borrower;
import com.collabera.austinteck.model.Borrowing;
import com.collabera.austinteck.model.request.BorrowingRequest;
import com.collabera.austinteck.model.response.BorrowingResponse;
import com.collabera.austinteck.repository.BookRepository;
import com.collabera.austinteck.repository.BorrowerRepository;
import com.collabera.austinteck.repository.BorrowingRepository;
import com.collabera.austinteck.serviceimpl.BorrowingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * BorrowingService unit test
 * @author Austin Teck
 */

@ExtendWith(MockitoExtension.class)
public class BorrowingServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BorrowerRepository borrowerRepository;

    @Mock
    private BorrowingRepository borrowingRepository;

    @InjectMocks
    private BorrowingServiceImpl borrowingServiceImpl;

    // ----------------------
    // Borrow book tests
    // ----------------------

    /**
     * [Unit Test] Borrow book success emulation
     */
    @Test
    void borrowBook_success() {
        // Create book
        Book book = new Book();
        book.setId(1L);
        book.setIsbn("9783376756818");
        book.setTitle("Game of Thrones");
        book.setAuthor("George RR Martin");

        // Create borrower
        Borrower borrower = new Borrower();
        borrower.setId(10L);
        borrower.setEmail("george.martin@gmail.com");

        // Validate saved book and borrower, 'false' when borrowing
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(borrowerRepository.findById(1L)).thenReturn(Optional.of(borrower));
        when(borrowingRepository.existsByBookAndReturnDateIsNull(book)).thenReturn(false);

        // Create borrowing
        Borrowing borrowing = new Borrowing();
        borrowing.setId(1L);
        borrowing.setBook(book);
        borrowing.setBorrower(borrower);
        borrowing.setBorrowDate(LocalDateTime.now());

        // Validate saved borrowing
        when(borrowingRepository.save(any(Borrowing.class))).thenReturn(borrowing);

        // Create BorrowingRequest DTO
        BorrowingRequest req = new BorrowingRequest(1L, 1L);

        // Create BorrowingResponse DTO
        BorrowingResponse res = borrowingServiceImpl.borrowBook(req);

        // Validate assertions
        assertEquals(10L, res.borrowerId());
        assertEquals(1L, res.bookId());
        assertEquals("Game of Thrones", res.title());
        assertEquals("george.martin@gmail.com", res.email());

        verify(borrowingRepository).save(any(Borrowing.class));
    }

    /**
     * [Unit Test] Book already borrowed emulation
     */
    @Test
    void borrowBook_bookAlreadyBorrowed_throwsException(){
        // Create book
        Book book = new Book();
        book.setId(1L);
        book.setIsbn("9794050977160");
        book.setTitle("Avatar");
        book.setAuthor("James Cameron");

        // Create borrower
        Borrower borrower = new Borrower();
        borrower.setId(1L);
        borrower.setEmail("james.cameron@gmail.com");

        // Validate book, borrower and return true on borrowing
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(borrowerRepository.findById(1L)).thenReturn(Optional.of(borrower));
        when(borrowingRepository.existsByBookAndReturnDateIsNull(book)).thenReturn(true);

        // Throw IllegalArgumentException = book already borrowed
        assertThrows(IllegalArgumentException.class, () -> borrowingServiceImpl.borrowBook(new BorrowingRequest(1L, 1L)));
    }

    /**
     * [Unit Test] Book not found emulation
     */
    @Test
    void borrowBook_bookNotFound_throwsException(){
        // Validate non-existent book - prepend lenient() as Exception might be thrown before stub is used
        lenient().when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // Throw IllegalArgumentException = book not found
        assertThrows(IllegalArgumentException.class, () -> borrowingServiceImpl.borrowBook(new BorrowingRequest(1L, 1L)));
    }

    /**
     * [Unit test] Borrower not found emulation
     */
    @Test
    void borrowBook_borrowerNotFound_throwsException(){
        // Validate non-existent borrower - prepend lenient() as Exception might be thrown before stub is used
        lenient().when(borrowerRepository.findById(1L)).thenReturn(Optional.empty());

        // Throw IllegalArgumentException = borrower not found
        assertThrows(IllegalArgumentException.class, () -> borrowingServiceImpl.borrowBook(new BorrowingRequest(1L, 1L)));
    }

    // ----------------------
    // Return book tests
    // ----------------------

    /**
     * [Unit Test] Return book success emulation
     */
    @Test
    void returnBook_success(){
        // Create book
        Book book = new Book();
        book.setId(1L);
        book.setIsbn("9793094342262");
        book.setTitle("Valve");
        book.setAuthor("Gabe Newell");

        // Create borrower
        Borrower borrower = new Borrower();
        borrower.setId(10L);
        borrower.setEmail("gabe.newell@gmail.com");

        // Create borrowing
        Borrowing borrowing = new Borrowing();
        borrowing.setId(1L);
        borrowing.setBook(book);
        borrowing.setBorrower(borrower);
        borrowing.setBorrowDate(LocalDateTime.now());

        // Validate book, and persist borrowing
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(borrowingRepository.findByBookAndReturnDateIsNull(any(Book.class))).thenReturn(Optional.of(borrowing));
        when(borrowingRepository.save(any(Borrowing.class))).thenAnswer(i -> i.getArgument(0));

        var res = borrowingServiceImpl.returnBook(new BorrowingRequest(1L, null));

        // Validate assertions
        assertNotNull(res.returnDate());
        assertEquals(10L, res.borrowerId());
        assertEquals("Valve", res.title());
    }

    /**
     * [Unit Test] Return not borrowed book emulation
     */
    @Test
    void returnBook_notBorrowed_throwsException(){
        // Create book
        Book book = new Book();
        book.setId(1L);
        book.setIsbn("9785034446888");
        book.setTitle("Apple");
        book.setAuthor("Steve Jobs");

        // Validate book, return empty on borrowing (return date = null -> book not borrowed)
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(borrowingRepository.findByBookAndReturnDateIsNull(book)).thenReturn(Optional.empty());

        // Throw IllegalArgumentException -> book cannot be returned if not borrowed
        assertThrows(IllegalArgumentException.class, () -> borrowingServiceImpl.returnBook(new BorrowingRequest(1L, 1L)));
    }

    /**
     * [Unit Test] Book not found upon return simulation
     */
    @Test
    void returnBook_bookNotFound_throwsException(){
        // Validate non-existent book - prepend lenient() as Exception might be thrown before stub is used
        lenient().when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // Throw IllegalArgumentException = book not found
        assertThrows(IllegalArgumentException.class, () -> borrowingServiceImpl.returnBook(new BorrowingRequest(1L, 1L)));
    }
}
