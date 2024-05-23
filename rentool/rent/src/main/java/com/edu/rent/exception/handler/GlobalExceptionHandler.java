package com.edu.rent.exception.handler;

import com.edu.rent.exception.AccessDeniedException;
import com.edu.rent.exception.ApiErrorResponse;
import com.edu.rent.exception.BadRequestException;
import com.edu.rent.exception.NotFoundException;
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

    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "403",
            description = "Пользователь не имеет доступа к ресурсу")
    })
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrorResponse handleAccessDeniedException(AccessDeniedException ex) {
        return ex.toApiErrorResponse();
    }

    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "400",
            description = "Некорректные параметры запроса")
    })
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrorResponse handleCustomBadRequestException(BadRequestException ex) {
        return ex.toApiErrorResponse();
    }


}
