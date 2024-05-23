package com.edu.rent.exception;

public class AccessDeniedException extends CustomException {
    public AccessDeniedException(String message) {
        super(message, "403");
    }
}
