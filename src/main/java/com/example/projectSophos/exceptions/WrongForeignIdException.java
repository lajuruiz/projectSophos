package com.example.projectSophos.exceptions;

public class WrongForeignIdException extends Exception {
    public WrongForeignIdException(String errorMessage) {
        super(errorMessage);
    }
}
