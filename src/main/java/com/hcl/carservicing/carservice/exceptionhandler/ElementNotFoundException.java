package com.hcl.carservicing.carservice.exceptionhandler;

public class ElementNotFoundException extends RuntimeException {

    public ElementNotFoundException(String message) {
        super(message);
    }
}
