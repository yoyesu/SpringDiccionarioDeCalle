package com.mdlsf.springdiccionariodecalle.exceptions;

public class InvalidInputChoice extends Throwable{

    public String getMessage(){
        return "Invalid choice. Please try again.";
    }
}
