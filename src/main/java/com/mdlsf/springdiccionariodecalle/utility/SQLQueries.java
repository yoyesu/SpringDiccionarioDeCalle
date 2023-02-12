package com.mdlsf.springdiccionariodecalle.utility;

public interface SQLQueries {

   // String SELECT_ENTRY_BY_NAME = "SELECT * FROM dictionary WHERE entry_name = ?";
    String SELECT_INDIVIDUAL_RECORDS = "SELECT * FROM dictionary WHERE ? LIKE ?";
    String[] columns = {"entry_name", "country_use"};

    String SELECT_ALL = "SELECT * FROM dictionary;";
    String DROP_TABLE = "DROP TABLE dictionary";
    String INSERT_INTO = "INSERT INTO dictionary " +
            "(id_number, entry_name, definition, date_added, country_use) " +
            "VALUES(?,?,?,?,?);";

    String CREATE_TABLE = "CREATE TABLE dictionary (" +
            "id_number VARCHAR(10) NOT NULL PRIMARY KEY," +
            "entry_name VARCHAR(30)," +
            "definition VARCHAR(300)," +
            "date_added DATE," +
            "country_use VARCHAR(30)" +
            ");";

}
