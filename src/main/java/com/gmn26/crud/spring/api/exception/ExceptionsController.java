package com.gmn26.crud.spring.api.exception;

import com.gmn26.crud.spring.api.bean.WebResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsController {

    @ExceptionHandler(value = ConstraintViolationException.class, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> handleConstraintViolationException(ConstraintViolationException ex) {
        WebResponse<String> response = WebResponse.<String>builder()
                .success(false)
                .message(ex.getMessage())
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
