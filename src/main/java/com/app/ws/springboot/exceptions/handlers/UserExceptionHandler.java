package com.app.ws.springboot.exceptions.handlers;

import com.app.ws.springboot.entities.User;
import com.app.ws.springboot.exceptions.UserException;
import com.app.ws.springboot.response.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(value = UserException.class)
    public ResponseEntity<Object> handleUserException(UserException ex, WebRequest res){
        ErrorResponse errorResponse = new ErrorResponse(new Date(),ex.getMessage());
        return new ResponseEntity(errorResponse, new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
