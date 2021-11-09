package com.app.ws.springboot.exceptions;

public class UserException extends RuntimeException{
    public UserException(String message) {
        super(message);
    }
}
