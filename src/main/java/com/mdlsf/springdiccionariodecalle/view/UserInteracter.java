package com.mdlsf.springdiccionariodecalle.view;

import com.mdlsf.springdiccionariodecalle.exceptions.InvalidInputChoice;
import com.mdlsf.springdiccionariodecalle.model.DAO;
import com.mdlsf.springdiccionariodecalle.model.DTO;

import java.util.Scanner;

public class UserInteracter {
    private static final Scanner sc = new Scanner(System.in);

    public static void printStartingMenu(){
        System.out.println("""
                               Welcome!
                               Choose one of the options below:
                                1. Search the dictionary
                                2. Add an entry
                                3. Exit""");
    };

    public static void getChoiceOfFeature(DAO dao, int userInitialChoice) throws InvalidInputChoice {

        switch(userInitialChoice){
            case 1 -> getSearchParameters(dao);
            case 2 -> getEntryInfo(dao);
            case 3 -> System.out.println("Bye!");
            default -> {
                throw new InvalidInputChoice();
            }
        }
    };

    private static void getEntryInfo(DAO dao) {
        System.out.println("Entry name:");
            String entryName = sc.nextLine();
        System.out.println("Definition:");
            String definition = sc.nextLine();
        System.out.println("Country of use:");
            String country = sc.nextLine();
        dao.insertOneEntryIntoTable(new DTO(entryName,definition,country), dao.connectingToDataBase());
    };

    private static void getSearchParameters(DAO dao) {
        System.out.println("Choose 0 to search terms by name. Choose 1 to search by country");
        int column = sc.nextInt();
        sc.nextLine();
        System.out.println("Introduce your search. Ex. \"panoli\" or \"mexico\"");
        String searchWord = sc.nextLine();
        dao.selectIndividualRecords(column,searchWord);
    };
}
