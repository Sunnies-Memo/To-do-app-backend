package com.sunniesfish.todo_app.global.error;

public class ErrorResponse {
    private String errorCode;
    private String message;

    // Constructor, Getter, and Setter
    public ErrorResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    // Getter and Setter
}