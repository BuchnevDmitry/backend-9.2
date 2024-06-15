package com.edu.tool.exception;

public class BadRequestException extends CustomException{
    public BadRequestException(String message) {
        super(message, "400");
    }
}
