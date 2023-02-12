package com.mdlsf.springdiccionariodecalle.model;

import com.mdlsf.springdiccionariodecalle.utility.SQLQueries;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

public class DAO {
    //private static LoggerConfig loggerConfig = LoggerConfig.getInstance();
    private static final Properties properties = new Properties();
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/diccionariocalle";

    public Connection connectingToDataBase() {
        Connection connection = null;
        try {
            properties.load(new FileReader("src/main/resources/login.properties"));
            connection = DriverManager.getConnection(DATABASE_URL, properties.getProperty("username"), properties.getProperty(
                    "password"));
            connection.setAutoCommit(false);
           // loggerConfig.myLogger.log(Level.INFO, "Connected to database.");
        } catch (IOException e) {
            //CustomLoggerConfiguration.myLogger.log(Level.WARNING, "IO exception caught.");
            e.printStackTrace();
        } catch (SQLException e) {
          //  CustomLoggerConfiguration.myLogger.log(Level.WARNING, "SQL exception caught.");
            e.printStackTrace();
        } catch (Exception e) {
          //  CustomLoggerConfiguration.myLogger.log(Level.WARNING, "Exception caught.");
            e.printStackTrace();
        }
        return connection;
    }

    public void disconnectingFromDatabase(Connection newConnection) {
        try {
            newConnection.close();
        } catch (Exception e) {
            //CustomLoggerConfiguration.myLogger.log(Level.WARNING, "SQL exception caught.");
            e.printStackTrace();
        }
    };

    public void createTable(Connection newConnection) {
        try (PreparedStatement preparedStatement = newConnection.prepareStatement(SQLQueries.CREATE_TABLE)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
//            loggerConfig.myLogger.log(Level.WARNING, "SQL exception.");
            throw new RuntimeException(e);
        }
    };

    public void createDictionaryTable() {
        Connection newConnection = connectingToDataBase();
        try {
            if (employeeTableCheck(newConnection)) {
//                loggerConfig.myLogger.log(Level.INFO, "dictionary table already exists.");
            } else {
                createTable(newConnection);
//                loggerConfig.myLogger.log(Level.INFO, "Table created.");

            }
            newConnection.commit();

        } catch (Exception e) {
//            loggerConfig.myLogger.log(Level.WARNING, "SQL exception.");

            throw new RuntimeException(e);
        } finally {
            disconnectingFromDatabase(newConnection);
        }
    };

    private boolean employeeTableCheck(Connection newConnection) {
        try {
            ResultSet resultSet = newConnection.getMetaData().getTables(null,
                    null, "dictionary", new String[]{"TABLE"});
            while (resultSet.next()) {
                String databaseName = resultSet.getString("TABLE_NAME");
                if (databaseName.equals("dictionary")) {
                    return true;
                }
            }
            resultSet.close();
        } catch (SQLException e) {
           // CustomLoggerConfiguration.myLogger.log(Level.WARNING, "SQL exception caught.");
            throw new RuntimeException(e);
        }
        return false;
    }

    public void dropTable(Connection newConnection) {
        try (PreparedStatement preparedStatement = newConnection.prepareStatement(SQLQueries.DROP_TABLE)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
           // CustomLoggerConfiguration.myLogger.log(Level.WARNING, "SQL exception.");
            throw new RuntimeException(e);
        }
    };

    public void selectAllEntries() {
        Connection newConnection = connectingToDataBase();
        try {
            Statement statement = newConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQLQueries.SELECT_ALL);
            if (resultSet != null) {
                while (resultSet.next()) {
                    System.out.println(resultSet.getString(1) + " " +
                            resultSet.getString(2) + " " +
                            resultSet.getString(3) + " " +
                            resultSet.getString(4) + " " +
                            resultSet.getString(5));
                }
            }
        } catch (SQLException e) {
           // CustomLoggerConfiguration.myLogger.log(Level.WARNING, "SQL exception.");
            throw new RuntimeException(e);
        }
    };

    public void insertSeveralEntriesIntoTable(List<DTO> entries, Connection newConnection) {
        try (PreparedStatement preparedStatement = newConnection.prepareStatement(SQLQueries.INSERT_INTO)) {
           for (DTO entry : entries) {
               prepareEntry(preparedStatement, entry);
               preparedStatement.addBatch();

            }
            preparedStatement.executeBatch();
            newConnection.commit();
        } catch (SQLException e) {
            //CustomLoggerConfiguration.myLogger.log(Level.WARNING, "SQL exception.");
            e.printStackTrace();
        }
    };

    public void insertOneEntryIntoTable(DTO entry, Connection newConnection) {
        try (PreparedStatement preparedStatement = newConnection.prepareStatement(SQLQueries.INSERT_INTO)) {
            prepareEntry(preparedStatement, entry);
            preparedStatement.addBatch();

            preparedStatement.executeBatch();
            newConnection.commit();
        } catch (SQLException e) {
            //CustomLoggerConfiguration.myLogger.log(Level.WARNING, "SQL exception.");
            e.printStackTrace();
        }
    };

    private static void prepareEntry(PreparedStatement preparedStatement, DTO entry) throws SQLException {
        preparedStatement.setInt(1, entry.getIdNumber());
        preparedStatement.setString(2, entry.getEntryName());
        preparedStatement.setString(3, entry.getDefinition());
        preparedStatement.setDate(4, entry.getDateAdded());
        preparedStatement.setString(5, entry.getCountryOfUse());
    };

    public void selectIndividualRecords(int column, String filter) {
        int count = 0;
        Connection newConnection = connectingToDataBase();
        try (PreparedStatement preparedStatement = newConnection.prepareStatement(SQLQueries.SELECT_INDIVIDUAL_RECORDS.replaceFirst("(\\?)+",SQLQueries.columns[column]))) {
            preparedStatement.setString(1, filter);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet != null) {
                while (resultSet.next()) {
                    System.out.println(
                            resultSet.getString(1) + " " +
                            resultSet.getString(2) + " " +
                            resultSet.getString(3) + " " +
                            resultSet.getString(4) + " " +
                            resultSet.getString(5));
                    count++;
                }
                //CustomLoggerConfiguration.myLogger.log(Level.FINE,
                  //      count + " records found when searching " + filter + " in column " + column);
                System.out.println(count + " records found.");
            } else {
               // CustomLoggerConfiguration.myLogger.log(Level.FINE,
                 //       "0 records found when searching " + filter + " in column " + column);
                System.out.println("0 records found");
            }


        } catch (SQLException e) {
           // CustomLoggerConfiguration.myLogger.log(Level.WARNING, "SQL exception.");
            e.printStackTrace();
        }
    }

}
