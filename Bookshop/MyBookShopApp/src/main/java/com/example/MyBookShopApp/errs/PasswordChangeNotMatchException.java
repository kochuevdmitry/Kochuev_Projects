package com.example.MyBookShopApp.errs;

public class PasswordChangeNotMatchException extends Exception {
    public PasswordChangeNotMatchException(String message) {
        super(message);
    }
}
