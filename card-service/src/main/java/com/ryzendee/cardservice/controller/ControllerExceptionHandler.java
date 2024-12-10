package com.ryzendee.cardservice.controller;

import com.ryzendee.cardservice.dto.response.ExceptionResponse;
import com.ryzendee.cardservice.dto.response.ValidationFieldErrorResponse;
import com.ryzendee.cardservice.exception.CardNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public ExceptionResponse handleCardNotFoundEx(CardNotFoundException ex) {
        return new ExceptionResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ExceptionResponse handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ExceptionResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public List<ValidationFieldErrorResponse> handleMethodArgumentNotValidEx(MethodArgumentNotValidException ex) {
        return ex.getFieldErrors().stream()
                .map(error -> new ValidationFieldErrorResponse(error.getField(), error.getDefaultMessage()))
                .toList();
    }
}
