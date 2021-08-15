package com.bbolab.gaonna.api.v1.controller.exception.validator;

public class BboxConstraintException extends RuntimeException{
    public BboxConstraintException(String message) {
        super(message);
    }
}
