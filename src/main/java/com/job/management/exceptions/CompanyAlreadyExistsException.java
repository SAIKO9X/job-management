package com.job.management.exceptions;

public class CompanyAlreadyExistsException extends RuntimeException {
    public CompanyAlreadyExistsException() {
        super("A company with the given email already exists.");
    }
}
