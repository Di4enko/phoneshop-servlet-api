package com.es.phoneshop.exception;

public class ItemNotFoundException extends RuntimeException{

    public ItemNotFoundException(String message) {
        super(message);
    }
}
