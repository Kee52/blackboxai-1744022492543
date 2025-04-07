package com.coffeeshop.exception;

public class InvalidMenuItemException extends Exception {
    public InvalidMenuItemException(String message) {
        super(message);
    }

    public InvalidMenuItemException(String message, Throwable cause) {
        super(message, cause);
    }
}