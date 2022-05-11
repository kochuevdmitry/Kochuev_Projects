package com.example.MyBookShopApp.errs;

public class EmptyPaymentException extends Exception {
    public EmptyPaymentException(String message) {
        super(message);
    }
}
