package com.homoloa.exception;

public class FileParseException extends Exception {

    private String message;

    public FileParseException(String message){
        super(message);
        this.message = message;
    }
    public FileParseException(){};
}
