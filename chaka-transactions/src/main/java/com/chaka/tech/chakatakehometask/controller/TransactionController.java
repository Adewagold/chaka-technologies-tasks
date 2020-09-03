package com.chaka.tech.chakatakehometask.controller;

import com.chaka.tech.chakatakehometask.exception.ValueNotFoundException;
import com.chaka.tech.chakatakehometask.model.ActionType;
import com.chaka.tech.chakatakehometask.model.Transaction;
import com.chaka.tech.chakatakehometask.service.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * *  Created by Adewale Adeleye on 2020-09-02
 **/
@RestController
public class TransactionController {
    private StatisticsService statisticsService;

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    public TransactionController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @PostMapping(value = "transactions", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Void> addTransaction(@Valid @RequestBody final Transaction transaction) throws ValueNotFoundException {
        ActionType actionType;
        try {
             actionType = statisticsService.addTransaction(transaction);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        switch (actionType){
            case DISCARDED:
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            case REGISTERED:
                return new ResponseEntity<>(HttpStatus.CREATED);
            default:
                throw new IllegalStateException();
        }

    }

    @DeleteMapping(value = "transactions")
    public ResponseEntity<Void> DeleteTransaction() {
        statisticsService.removeAll();
        logger.debug("all transactions deleted from storage");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
