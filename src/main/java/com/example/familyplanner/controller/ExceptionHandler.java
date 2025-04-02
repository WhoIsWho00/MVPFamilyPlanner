package com.example.familyplanner.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.example.familyplanner.dto.responses.ErrorResponseDto;
import com.example.familyplanner.service.exception.AlreadyExistException;
import com.example.familyplanner.service.exception.ExcessRegistrationLimitException;
import com.example.familyplanner.service.exception.ExpiredTokenException;
import com.example.familyplanner.service.exception.InvalidTokenException;
import com.example.familyplanner.service.exception.NotFoundException;
import com.example.familyplanner.service.exception.UserAlreadyExistException;
import com.example.familyplanner.service.exception.ValidationException;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> fieldError.getDefaultMessage() != null
                                ? fieldError.getDefaultMessage()
                                : "Invalid value"
                ));

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Validation Failed");
        response.put("errors", errors);
        return ResponseEntity.badRequest().body(response);
    }
  
    @org.springframework.web.bind.annotation.ExceptionHandler(ExcessRegistrationLimitException.class)
    public ResponseEntity<String> handleExcessRegistrationLimitException(NullPointerException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.TOO_MANY_REQUESTS);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({
            NotFoundException.class,
            AlreadyExistException.class,
            ValidationException.class,
            NullPointerException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<Map<String, Object>> handleBadRequestExceptions(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status",
                ex instanceof NotFoundException ? HttpStatus.NOT_FOUND.value() : HttpStatus.BAD_REQUEST.value()
        );
        response.put("error",
                ex instanceof NotFoundException ? "Not Found" : "Bad Request"
        );
        response.put("message", ex.getMessage());

        return ResponseEntity.status(
                ex instanceof NotFoundException ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST
        ).body(response);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({
            ExpiredTokenException.class,
            InvalidTokenException.class
    })
    public ResponseEntity<ErrorResponseDto> handleTokenExceptions(Exception ex) {
        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .error(ex instanceof ExpiredTokenException
                        ? "Token has expired"
                        : "Invalid token")
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> handleMethodNotAllowedException(HttpRequestMethodNotSupportedException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.METHOD_NOT_ALLOWED.value());
        response.put("error", "Method Not Allowed");
        response.put("message", "Only POST method is allowed for this endpoint");
        response.put("supportedMethods", ex.getSupportedMethods());

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header("Allow", "POST")
                .body(response);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadable(org.springframework.http.converter.HttpMessageNotReadableException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", new java.util.Date());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Bad Request");
        response.put("message", "Request body is missing or malformed");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<String> handleUserAlreadyExistException(UserAlreadyExistException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}