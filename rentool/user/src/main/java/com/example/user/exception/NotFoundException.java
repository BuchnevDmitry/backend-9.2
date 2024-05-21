package com.example.user.exception;

public class NotFoundException extends CustomException {

    public NotFoundException(String message) {
        super(message, "404");
    }
}
