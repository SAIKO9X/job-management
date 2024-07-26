package com.job.management.exceptions;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException() {
        super("email/password incorrect");
    }
}
