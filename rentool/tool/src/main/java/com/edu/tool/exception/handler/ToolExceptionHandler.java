package com.edu.tool.exception.handler;

import com.edu.tool.api.contoller.ToolController;
import com.edu.tool.exception.ApiErrorResponse;
import com.edu.tool.exception.BadRequestException;
import com.edu.tool.exception.NotFoundException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = ToolController.class)
public class ToolExceptionHandler {
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "404",
            description = "Инструмент не существует")
    })
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleNotFoundException(NotFoundException ex) {
        return ex.toApiErrorResponse();
    }

    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "400",
            description = "Некорректные параметры запроса")
    })
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleBadRequestException(BadRequestException ex) {
        return ex.toApiErrorResponse();
    }
}
