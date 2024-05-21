package com.example.user.exception.handler;

import com.example.user.exception.ApiErrorResponse;
import com.example.user.exception.NotFoundException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "400",
            description = "Некорректные параметры запроса")
    })
    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleBadRequestException() {
        return new ApiErrorResponse(
            "400",
            "Некорректные параметры запроса"
        );
    }

    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "404",
            description = "Пользователь не существует")
    })
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleNotFoundException(NotFoundException ex) {
        return ex.toApiErrorResponse();
    }
}
