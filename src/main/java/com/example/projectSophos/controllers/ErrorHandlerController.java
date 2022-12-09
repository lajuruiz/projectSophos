package com.example.projectSophos.controllers;

import com.example.projectSophos.exceptions.WrongForeignIdException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;

@RestControllerAdvice
public class ErrorHandlerController {

    @ExceptionHandler(WrongForeignIdException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handleWrongForeignIdError(WrongForeignIdException ex) {
        return ex.getMessage();
    }


}
