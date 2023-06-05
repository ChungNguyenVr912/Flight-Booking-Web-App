package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {
    private static String jdbcURL = "jdbc:mysql://localhost:3306/flight_booking?useSSL=false";
    private static String jdbcUsername = "root";
    private static String jdbcPassword = "Cvr*317719#";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
