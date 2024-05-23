package com.edu.rent.exception;

public record ApiErrorResponse(
    String code,
    String message
) {
}
