package com.example.user.exception;

public class KeycloakException extends CustomException {
    public KeycloakException(String message) {
        super(message, "400");
    }
}
