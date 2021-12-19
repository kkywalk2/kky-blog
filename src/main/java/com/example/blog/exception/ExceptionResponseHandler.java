package com.example.blog.exception;

import com.example.blog.dto.ResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.blog.dto.ExceptionResponse;

@ControllerAdvice
public class ExceptionResponseHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<ExceptionResponse> handleException(Exception ex) {
        ExceptionResponse bodyOfResponse = new ExceptionResponse(ResponseCode.UNKNOWN_ERROR, ex.toString());
        return new ResponseEntity<>(bodyOfResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
