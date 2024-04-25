package com.edu.tool.exception.handler;

import com.edu.tool.api.contoller.BrandController;
import com.edu.tool.exception.ApiErrorResponse;
import com.edu.tool.exception.NotFoundException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = BrandController.class)
public class BrandExceptionHandler {
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "404",
            description = "Бренд не существует")
    })
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleNotFoundException(NotFoundException ex) {
        return ex.toApiErrorResponse();
    }
}
