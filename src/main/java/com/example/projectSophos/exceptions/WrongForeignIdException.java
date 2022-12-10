package com.example.projectSophos.exceptions;

public class WrongForeignIdException extends Exception {
    private String field;
    public WrongForeignIdException(String errorMessage, String field) {
        super(errorMessage);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
