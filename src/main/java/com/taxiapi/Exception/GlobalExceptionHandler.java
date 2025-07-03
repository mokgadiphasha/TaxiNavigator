package com.taxiapi.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final ExceptionUtil util = new ExceptionUtil();


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> userInputInvalid(MethodArgumentNotValidException ex){

        Map<String,String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField,
                        FieldError::getDefaultMessage
                ));

        return util
                .errorBody(HttpStatus.BAD_REQUEST,errors);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,Object> resourceNotFound(ResourceNotFoundException ex){
        return util.errorBody(HttpStatus.NOT_FOUND,ex.getMessage());
    }


    @ExceptionHandler(DuplicateResourceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String,Object> duplicateResource(DuplicateResourceException ex){
        return util.errorBody(HttpStatus.CONFLICT,ex.getMessage());
    }


}
