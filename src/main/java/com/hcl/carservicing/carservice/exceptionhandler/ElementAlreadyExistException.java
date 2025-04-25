package com.hcl.carservicing.carservice.exceptionhandler;

public class ElementAlreadyExistException extends RuntimeException{

    public ElementAlreadyExistException(String message) {
        super(message);
    }
}
