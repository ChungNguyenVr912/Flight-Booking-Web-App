package dao;

import connection.JDBCConnection;
import model.Flight;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightDAO {
    private static final String INSERT_FLIGHT_SQL =
            "INSERT INTO flight(id, flight_code, airlines_ID, airplane_ID, departure, destination, depart_time, arrival_time, base_price) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String SELECT_FLIGHT_SQL =
            "select * from flight where departure = ? and destination = ?";

    public static void insertFlight(List<Flight> flights) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = JDBCConnection.getConnection();
            statement = connection.prepareStatement(INSERT_FLIGHT_SQL);
            connection.setAutoCommit(false);
            int i = 0;
            for (Flight flight : flights) {
                String id = flight.getId();
                String flightCode = flight.getFlightCode();
                long airlinesID = flight.getAirLinesID();
                long airplaneID = flight.getAirPlaneID();
                String departure = flight.getDeparture();
                String destination = flight.getDestination();
                LocalDateTime departTime = flight.getDepartTime();
                LocalDateTime arrivalTime = flight.getArrivalTime();
                double basePrice = flight.getBasePrice();
                statement.setString(1, id);
                statement.setString(2, flightCode);
                statement.setLong(3, airlinesID);
                statement.setLong(4, airplaneID);
                statement.setString(5, departure);
                statement.setString(6, destination);
                statement.setTimestamp(7, Timestamp.valueOf(departTime));
                statement.setTimestamp(8, Timestamp.valueOf(arrivalTime));
                statement.setDouble(9, basePrice);
                statement.addBatch();
                if (i == 1000) {
                    i = 0;
                    statement.executeBatch();
                    statement.clearBatch();
                }
                i++;
            }
            statement.executeBatch();
            statement.clearBatch();
            connection.commit();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (statement != null) statement.close();
                connection.close();
                connection.rollback();
            } catch (SQLException exception) {
                e.printStackTrace();
            }
        }
    }

    public static List<Flight> selectFlight(String departure, String destination) {
        List<Flight> flights = new ArrayList<>();
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_FLIGHT_SQL)) {
            statement.setString(1, departure);
            statement.setString(2, destination);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                flights.add(Flight.builder()
                        .id(resultSet.getString("id"))
                        .flightCode(resultSet.getString("flight_code"))
                        .airLinesID(resultSet.getLong("airlines_ID"))
                        .airPlaneID(resultSet.getLong("airplane_ID"))
                        .departure(departure)
                        .destination(destination)
                        .departTime(resultSet.getTimestamp("depart_time").toLocalDateTime())
                        .arrivalTime(resultSet.getTimestamp("arrival_time").toLocalDateTime())
                        .basePrice(resultSet.getDouble("base_price"))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }
}
