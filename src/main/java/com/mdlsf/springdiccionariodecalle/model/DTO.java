package com.mdlsf.springdiccionariodecalle.model;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class DTO {
    private static int idNumber = 0;
    private String entryName;
    private String definition;
    private final long milliseconds = System.currentTimeMillis();
    private final Date dateAdded = new Date(milliseconds);
    private String countryOfUse;

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    public DTO(String entryName, String definition, String countryOfUse) {
        idNumber++;
        this.entryName = entryName;
        this.definition = definition;
        SIMPLE_DATE_FORMAT.format(dateAdded);
        this.countryOfUse = countryOfUse;

    }

    public int getIdNumber() {
        return idNumber;
    }

    public String getEntryName() {
        return entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public String getCountryOfUse() {
        return countryOfUse;
    }

    public void setCountryOfUse(String countryOfUse) {
        this.countryOfUse = countryOfUse;
    }

    @Override
    public String toString(){
        return "Entry #"
                + idNumber
                + "\n"
                + entryName
                + "\nDefinition: " + definition
                + "\nCountry where it is used: " + countryOfUse
                + "\nDate added: " + dateAdded;
    }
}
