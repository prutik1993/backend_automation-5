package database;
import java.sql.*;
public class DatabaseConnection {
    public static void main(String[] args) throws SQLException {

        /**
         * In order to connect to the database, we need our URL, username, password and query
         * NOTE: This can be the interview question
         */


        String query = "select * from employees";
        String url = "jdbc:oracle:thin:@techglobal.cup7q3kvh5as.us-east-2.rds.amazonaws.com:1521/TGDEVQA";
        String username = "anastasiya";
        String password = "$anastasiya123!";


        // create a connection to the database to parameters we stored
        Connection connection = DriverManager.getConnection(url, username, password);

        System.out.println("Database connection is successful");

        // statement keep the connection between DB and Automation to send queries
        Statement statement = connection.createStatement();

        // ResultSet is sending the query to the database and get the result
        ResultSet resultSet = statement.executeQuery(query);

        // ResultSetMetaDate gets the information about the date,
        // you can't simply print the column values.
        // we need to call them with iterations
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        System.out.println("Column name " + resultSetMetaData.getColumnName(1));
        System.out.println("Number of columns " + resultSetMetaData.getColumnCount());

        while(resultSet.next()){
            System.out.println(resultSet.getString("FIRST_NAME"));
            System.out.println(resultSet.getString("LAST_NAME"));
            System.out.println("---------------");
        }
    }
}
