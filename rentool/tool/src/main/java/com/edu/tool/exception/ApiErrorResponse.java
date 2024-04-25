package com.edu.tool.exception;

public record ApiErrorResponse(
    String code,
    String message
) {
}
