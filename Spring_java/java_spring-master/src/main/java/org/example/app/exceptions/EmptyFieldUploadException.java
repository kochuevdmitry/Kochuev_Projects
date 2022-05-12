package org.example.app.exceptions;

public class EmptyFieldUploadException extends Exception{

    private final String message;

    public EmptyFieldUploadException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
