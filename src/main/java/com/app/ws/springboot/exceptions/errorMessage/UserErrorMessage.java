package com.app.ws.springboot.exceptions.errorMessage;

public enum UserErrorMessage {
    NOT_FOUND("User not found with this id"),
    FIELD_ALREADY_USER("email already used");
    private String message;
    UserErrorMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
