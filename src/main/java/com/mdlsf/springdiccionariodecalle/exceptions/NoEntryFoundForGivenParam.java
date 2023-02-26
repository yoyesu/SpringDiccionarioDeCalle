package com.mdlsf.springdiccionariodecalle.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoEntryFoundForGivenParam extends RuntimeException{

    private String message;

    public NoEntryFoundForGivenParam(String message) {
        this.message = message;
    }

    public String getMessage(String word) {
        return "No entries were found for the parameter " + word;
    }
}
