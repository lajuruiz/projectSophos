package com.example.projectSophos.controllers;

import com.example.projectSophos.exceptions.WrongForeignIdException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@RestControllerAdvice
public class ErrorHandlerController {

    @ExceptionHandler(WrongForeignIdException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiError handleWrongForeignIdError(WrongForeignIdException ex) {
        ApiError error = new ApiError(NOT_FOUND.value(), "validation foreign key error");
        error.addFieldError(ex.getField(), ex.getMessage());
        return error;
    }


    @ResponseStatus(NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiError methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<org.springframework.validation.FieldError> fieldErrors = result.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }

    private ApiError processFieldErrors(List<org.springframework.validation.FieldError> fieldErrors) {
        ApiError error = new ApiError(BAD_REQUEST.value(), "validation error");
        for (org.springframework.validation.FieldError fieldError: fieldErrors) {
            error.addFieldError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return error;
    }

    class ApiError {
        private final int status;
        private final String error;
        private final List<FieldError> fieldErrors = new ArrayList<>();

        ApiError(int status, String message) {
            this.status = status;
            this.error = message;
        }

        public int getStatus() {
            return status;
        }

        public String getError() {
            return error;
        }

        public void addFieldError(String field, String message)  {
            FieldError error = new FieldError(field, message);
            fieldErrors.add(error);
        }

        public List<FieldError> getFieldErrors() {
            return fieldErrors;
        }
    }

    class FieldError {
        private final String field;
        private final String message;

        FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }
    }

}
