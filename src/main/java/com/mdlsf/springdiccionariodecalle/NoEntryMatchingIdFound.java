package com.mdlsf.springdiccionariodecalle;

public class NoEntryMatchingIdFound extends Throwable{

    public String getMessage(int id) {
        return "No entry was found with id " + id;
    }
}
