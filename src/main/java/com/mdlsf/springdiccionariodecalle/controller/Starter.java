package com.mdlsf.springdiccionariodecalle.controller;

import com.mdlsf.springdiccionariodecalle.exceptions.InvalidInputChoice;
import com.mdlsf.springdiccionariodecalle.model.DAO;
import com.mdlsf.springdiccionariodecalle.view.UserInteracter;

import java.util.Scanner;
import java.util.logging.Level;

public class Starter {
    private static final Scanner sc = new Scanner(System.in);
   // private static LoggerConfig loggerConfig = LoggerConfig.getInstance();

    public static void start(){
        try {
           // loggerConfig.myLogger.log(Level.INFO, "App started.");
            DAO dao = new DAO();
            dao.createDictionaryTable();
            UserInteracter.printStartingMenu();
            UserInteracter.getChoiceOfFeature(dao, sc.nextInt());
        } catch (InvalidInputChoice e){
            System.out.println(e.getMessage());
            System.out.println("------------------------------");
            start();
        }

    }


}
