package com.chaka.tech.chakatakehometask.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.Instant;

/**
 * *  Created by Adewale Adeleye on 2020-09-02
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private HttpStatus status;
    private String errorCode;
    private Instant timestamp = Instant.now();
    private String message;
    private String details;
    private String type;

    public ErrorResponse(String message, String details) {
        this.message = message;
        this.details = details;
    }

    public ErrorResponse() {
    }

    public ErrorResponse(String message, String details, String type) {
        this.message = message;
        this.details = details;
        this.type = type;
    }

    public ErrorResponse(HttpStatus status, String errorCode, String message, String details) {
        super();
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
        this.details = details;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
