package com.mdlsf.springdiccionariodecalle;

public class NoMatchingIdFound extends Throwable{

    public String getMessage(int id) {
        return "The id " + id + " could not be found in the database. Please, provide a valid id.";
    }
}
