package com.edu.tool.exception;

abstract class CustomException extends RuntimeException {

    private final String code;

    CustomException(String message, String code) {
        super(message);
        this.code = code;
    }

    public ApiErrorResponse toApiErrorResponse() {
        return new ApiErrorResponse(
            code,
            this.getMessage()
        );
    }
}
