package com.example.securityloginsignup.exception;

public class AccountAlreadyExistException extends RuntimeException{
    public AccountAlreadyExistException(String message) {
        super(message);
    }
}
