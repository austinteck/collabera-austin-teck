package com.collabera.austinteck.model.response;

import java.time.LocalDateTime;

/**
 * BorrowingResponse DTO
 * @author : Austin Teck
*/

public record BorrowingResponse (
        Long bookId,
        Long borrowerId,
        String title,
        String author,
        String email,
        LocalDateTime borrowDate,
        LocalDateTime returnDate
){}
