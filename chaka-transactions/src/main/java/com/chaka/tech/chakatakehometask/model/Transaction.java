package com.chaka.tech.chakatakehometask.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * *  Created by Adewale Adeleye on 2020-09-02
 **/
public class Transaction {
    @NotNull(message = "amount cannot be null")
    private final BigDecimal amount;
    @NotNull(message = "timestamp is needed and cannot be null")
    private final Instant timestamp;

    @JsonCreator
    public Transaction(@JsonProperty("amount") BigDecimal amount, @JsonProperty("timestamp") Instant timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
