package com.collabera.austinteck.serviceimpl;

import com.collabera.austinteck.model.Book;
import com.collabera.austinteck.model.Borrower;
import com.collabera.austinteck.model.Borrowing;
import com.collabera.austinteck.model.request.BorrowingRequest;
import com.collabera.austinteck.model.response.BorrowingResponse;
import com.collabera.austinteck.repository.BookRepository;
import com.collabera.austinteck.repository.BorrowerRepository;
import com.collabera.austinteck.repository.BorrowingRepository;
import com.collabera.austinteck.service.BorrowingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BorrowingServiceImpl implements BorrowingService {

    private static final Logger log = LoggerFactory.getLogger(BorrowingServiceImpl.class);

    private final BookRepository bookRepository;
    private final BorrowerRepository borrowerRepository;
    private final BorrowingRepository borrowingRepository;

    public BorrowingServiceImpl(BookRepository bookRepository, BorrowerRepository borrowerRepository, BorrowingRepository borrowingRepository) {
        this.bookRepository = bookRepository;
        this.borrowerRepository = borrowerRepository;
        this.borrowingRepository = borrowingRepository;
    }

    /**
     * Borrow book
     * @param borrowingRequest BorrowingRequest DTO
     * @return BorrowingResponse DTO
     */

    @Override
    public BorrowingResponse borrowBook(BorrowingRequest borrowingRequest){
        // Validate book
        Book book = bookRepository.findById(borrowingRequest.bookId())
                .orElseThrow(() -> new IllegalArgumentException("Book: " + borrowingRequest.bookId() + " not found"));

        // Validate borrower
        Borrower borrower = borrowerRepository.findById(borrowingRequest.borrowerId())
                .orElseThrow(() -> new IllegalArgumentException("Borrower: " + borrowingRequest.borrowerId() + " not found"));

        // Validate borrowing
        if (borrowingRepository.existsByBookAndReturnDateIsNull(book)){
            log.error("Book: {} is already borrowed", borrowingRequest.bookId());
            throw new IllegalArgumentException("Book: " + borrowingRequest.bookId() + " is already borrowed");
        }

        // Persist
        Borrowing res = new Borrowing();
        res.setBook(book);
        res.setBorrower(borrower);
        res.setBorrowDate(LocalDateTime.now());

        log.info("Book: {} successfully borrowed by: {}", res.getBook().getId(), res.getBorrower().getId());
        return mapRes(borrowingRepository.save(res), true);
    }

    /**
     * Return borrowed book
     * @param borrowingRequest BorrowingRequest DTO
     * @return BorrowingResponse DTO
     */
    @Override
    public BorrowingResponse returnBook(BorrowingRequest borrowingRequest){
        // Validate book
        Book book = bookRepository.findById(borrowingRequest.bookId())
                .orElseThrow(() -> new IllegalArgumentException("Book: " + borrowingRequest.bookId() + " not found"));

        // Validate borrowed book
        Borrowing borrowing = borrowingRepository.findByBookAndReturnDateIsNull(book)
                .orElseThrow(() -> new IllegalArgumentException("Book: " + borrowingRequest.bookId() + " is not currently borrowed"));

        // Update return date
        borrowing.setReturnDate(LocalDateTime.now());
        log.info("Book: {} successfully returned by: {}", borrowing.getBook().getId(), borrowing.getBorrower().getId());

        // Persist
        return mapRes(borrowingRepository.save(borrowing), false);
    }

    /**
     * Auxiliary method to map BorrowingResponse DTO fields
     * @param borrowing Borrowing DTO
     * @return BorrowingResponse DTO
     */
    private BorrowingResponse mapRes(Borrowing borrowing, boolean isBorrow){
        return new BorrowingResponse(
                borrowing.getBook().getId(),
                borrowing.getBorrower().getId(),
                borrowing.getBook().getTitle(),
                borrowing.getBook().getAuthor(),
                borrowing.getBorrower().getEmail(),
                isBorrow ? borrowing.getBorrowDate() : null,
                isBorrow ? null : borrowing.getReturnDate()
        );
    }

}
