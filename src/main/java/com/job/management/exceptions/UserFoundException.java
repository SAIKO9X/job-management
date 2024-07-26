package com.job.management.exceptions;

public class UserFoundException extends RuntimeException {
    public UserFoundException() {
        super("Usuário já existe");
    }
}
