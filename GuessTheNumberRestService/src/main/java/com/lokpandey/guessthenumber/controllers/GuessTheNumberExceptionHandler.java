/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: Jan 20, 2023
 * purpose: Exception handler for controller
 */

package com.lokpandey.guessthenumber.controllers;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class GuessTheNumberExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String CONSTRAINT_MESSAGE = "Game does not exist. "
            + "Please ensure it is valid and try again.";

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public final ResponseEntity<Error> handleSqlException(
            EmptyResultDataAccessException ex,
            WebRequest request) {

        Error err = new Error();
        err.setMessage(CONSTRAINT_MESSAGE);
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }
}
