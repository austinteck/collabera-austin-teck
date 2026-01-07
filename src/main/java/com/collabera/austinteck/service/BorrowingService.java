package com.collabera.austinteck.service;

import com.collabera.austinteck.model.request.BorrowingRequest;
import com.collabera.austinteck.model.response.BorrowingResponse;

/**
 * Borrowing Service
 * @author : Austin Teck
 */

public interface BorrowingService {

    BorrowingResponse borrowBook(BorrowingRequest borrowingRequest);

    BorrowingResponse returnBook(BorrowingRequest borrowingRequest);
}
