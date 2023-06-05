package dao;

import connection.JDBCConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class AirPortDAO {
    private static final String SELECT_ALL_AIRPORT = "select * from air_port";

    public static HashMap<String, Integer> getAirPortDistance() {
        HashMap<String, Integer> airPorts = new HashMap<>();
        try (Connection connection = JDBCConnection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_AIRPORT);
            while (resultSet.next()) {
                airPorts.put(resultSet.getString("code")
                        , resultSet.getInt("unreal_position"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return airPorts;
    }

    public static HashMap<String, String> getAirPortName() {
        HashMap<String, String> airPorts = new HashMap<>();
        try (Connection connection = JDBCConnection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_AIRPORT);
            while (resultSet.next()) {
                airPorts.put(resultSet.getString("code")
                        , resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return airPorts;
    }
}
