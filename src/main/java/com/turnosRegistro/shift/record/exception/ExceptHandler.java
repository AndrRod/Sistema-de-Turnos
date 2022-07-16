package com.turnosRegistro.shift.record.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@ControllerAdvice
public class ExceptHandler {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<MessagesInfo> handleValidationError(HttpServletRequest request, MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getFieldErrors().stream().forEach(
                excep -> Stream.of(excep).forEach(e->
                        errors.put(e.getField(), e.getDefaultMessage()))
        );
        return ResponseEntity.badRequest().body(new MessagesInfo(errors, HttpStatus.BAD_REQUEST.value(), request.getRequestURI()));
    }
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<MessagesInfo> handleValidationError(HttpServletRequest request, ConstraintViolationException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getConstraintViolations().stream().forEach((e)->
                errors.put(e.getPropertyPath().toString().substring(e.getPropertyPath().toString().lastIndexOf('.') + 1), e.getMessage())
        );
        return ResponseEntity.badRequest().body(new MessagesInfo(errors, HttpStatus.BAD_REQUEST.value(), request.getRequestURI()));
    }
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<MessageInfo> badRequestException(BadRequestException ex, HttpServletRequest request){
        return ResponseEntity.badRequest().body(new MessageInfo(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), request.getRequestURL().toString()));
    }
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<MessageInfo> notFoundException(NotFoundException ex, HttpServletRequest request){
        return ResponseEntity.badRequest().body(new MessageInfo(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), request.getRequestURL().toString()));
    }
    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    public ResponseEntity<MessageInfo> sQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex, HttpServletRequest request){
        return ResponseEntity.badRequest().body(new MessageInfo(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), request.getRequestURL().toString()));
    }
}
