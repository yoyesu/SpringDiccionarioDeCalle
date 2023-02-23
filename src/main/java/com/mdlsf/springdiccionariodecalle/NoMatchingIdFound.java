package com.mdlsf.springdiccionariodecalle;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoMatchingIdFound extends Throwable{

    public String getMessage(int id) {
        return "The id " + id + " could not be found in the database. Please, provide a valid id.";
    }
}
