package com.collabera.austinteck.controller;

import com.collabera.austinteck.model.Borrower;
import com.collabera.austinteck.service.BorrowerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Borrower Controller
 * @author : Austin Teck
 */

@RestController
@RequestMapping("/api/borrower")
public class BorrowerController {

    private static final Logger log = LoggerFactory.getLogger(BorrowerController.class);

    private final BorrowerService borrowerService;

    @Autowired
    private BorrowerController(BorrowerService borrowerService){
        this.borrowerService = borrowerService;
    }

    /**
     * CREATE new borrower (/api/borrower/create)
     * @param borrower Borrower DTO
     * @return Created borrower
     */
    @PostMapping("/create")
    public Borrower createBorrower(@Valid @RequestBody Borrower borrower){
        log.info("Creating borrower: {}", borrower.getEmail());
        return borrowerService.createBorrower(borrower);
    }

}
