package com.collabera.austinteck.serviceimpl;

import com.collabera.austinteck.model.Borrower;
import com.collabera.austinteck.repository.BorrowerRepository;
import com.collabera.austinteck.service.BorrowerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BorrowerServiceImpl implements BorrowerService {

    private static final Logger log = LoggerFactory.getLogger(BorrowerServiceImpl.class);

    private final BorrowerRepository borrowerRepository;

    public BorrowerServiceImpl(BorrowerRepository borrowerRepository){
        this.borrowerRepository = borrowerRepository;
    }

    /**
     * Validate and persist borrower DTO
     * @param borrower Borrower DTO
     * @return Created borrower
     */
    @Override
    public Borrower createBorrower(Borrower borrower){
        // Validate borrower email
        if (borrowerRepository.findByEmail(borrower.getEmail()).isPresent()){
            log.error("Borrower email: {} already exists", borrower.getEmail());
            throw new IllegalArgumentException("Borrower email: " + borrower.getEmail() + " already exists");
        }

        // Persist
        log.info("Borrower Name: {}, Email: {} - successfully created", borrower.getName(), borrower.getEmail());
        return borrowerRepository.save(borrower);
    }
}
