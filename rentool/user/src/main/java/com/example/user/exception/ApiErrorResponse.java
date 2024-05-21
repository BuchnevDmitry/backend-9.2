package com.example.user.exception;

public record ApiErrorResponse(
    String code,
    String message
) {
}
