package com.bbolab.gaonna.api.exception;

import com.bbolab.gaonna.api.exception.validator.BboxConstraintException;
import com.bbolab.gaonna.api.v1.controller.QuestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice(assignableTypes = QuestController.class)
@RestController
public class QuestExceptionAdvisor {

    @ExceptionHandler(BboxConstraintException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected String bboxConstraintFailedException(HttpServletRequest request, BboxConstraintException e) {
        return e.getMessage();
    }
}
