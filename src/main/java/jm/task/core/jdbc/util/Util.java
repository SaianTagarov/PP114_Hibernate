package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соединения с БД
    public static Connection getMyConnection() throws SQLException,
            ClassNotFoundException {
        String hostName = "localhost";
        String dbName = "task1_db";
        String userName = "root";
        String password = "root21";
        Class.forName("com.mysql.cj.jdbc.Driver");
        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;
        return DriverManager.getConnection(connectionURL, userName, password);
    }
}
