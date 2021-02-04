package com.tekup.ds.rest.controller.exception;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(body(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDefinitionException.class)
    public ResponseEntity handleInvalidDefinitionException(InvalidDefinitionException ex) {
        return new ResponseEntity<>(body(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleIllegalArgumentException(NotFoundException ex) {
        return new ResponseEntity<>(body(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    private Map<String, String> body(String message) {
        Map<String, String> map = new HashMap<>();
        map.put("message", message);
        return map;
    }

}

