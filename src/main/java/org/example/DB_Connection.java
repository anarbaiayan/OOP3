package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_Connection {
    protected static String url = "jdbc:postgresql://localhost:2222/Project";
    protected static String name = "postgres";
    protected static String pass = "kanekiken_7";

    public static Connection connection_to_db() {
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(url, name, pass);
        }
        catch (Exception e){
            System.out.println(e);
        }
        return connection;
    }


}
