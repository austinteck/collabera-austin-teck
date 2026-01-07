package com.collabera.austinteck.model.request;

/**
 * BorrowingRequest DTO
 * @author : Austin Teck
*/

public record BorrowingRequest(
        Long bookId,
        Long borrowerId
) {}
