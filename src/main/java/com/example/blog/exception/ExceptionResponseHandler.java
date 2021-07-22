package com.example.blog.exception;

import com.example.blog.dto.ResponseCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.blog.dto.ExceptionResponse;

@ControllerAdvice
public class ExceptionResponseHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ExceptionResponse> handleException(Exception ex) {
        ExceptionResponse bodyOfResponse = new ExceptionResponse(ResponseCode.UNKNOWN_ERROR, ex.toString());
        return new ResponseEntity(bodyOfResponse, HttpStatus.BAD_REQUEST);
    }
}
