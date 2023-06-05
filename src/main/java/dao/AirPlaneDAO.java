package dao;

import connection.JDBCConnection;
import model.abstraction.AirPlane;
import model.abstraction.Seat;
import model.impl.BusinessAirplane;
import model.impl.EconomyAirplane;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AirPlaneDAO {
    private static final String SELECT_ALL_PLANE
            = "select * from airplane";
    private static final String INSERT_PLANE_SQL
            = "insert into airplane(name, type) VALUES (?,?)";
    private static final String INSERT_SEAT_SQL
            = "insert into seat(airplane_id,flight_id, seat_code, booked, price_multi) VALUES (?,?,?,?,?)";

    public static List<AirPlane> selectAllPlane() {
        List<AirPlane> airPlanes = new ArrayList<>();
        try (Connection connection = JDBCConnection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_PLANE);
            while (resultSet.next()) {
                String type = resultSet.getString("type");
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                switch (type) {
                    case "economy" -> airPlanes.add(EconomyAirplane.builder()
                            .id(id)
                            .name(name)
                            .build());
                    case "business" -> airPlanes.add(BusinessAirplane.builder()
                            .id(id)
                            .name(name)
                            .build());
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return airPlanes;
    }

    public static void insertPlaneList(List<AirPlane> airPlanes) {
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_PLANE_SQL)) {
            for (AirPlane plane : airPlanes) {
                String name = plane.getName();
                String type = (plane instanceof BusinessAirplane) ? "business" : "economy";
                statement.setString(1, name);
                statement.setString(2, type);
                statement.addBatch();
            }
            statement.executeBatch();
            System.out.println("insert planes complete!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertSeatList(List<Seat> seatList) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = JDBCConnection.getConnection();
            statement = connection.prepareStatement(INSERT_SEAT_SQL);
            connection.setAutoCommit(false);
            int i = 0;
            for (Seat seat : seatList) {
                long airPlaneID = seat.getAirPlaneID();
                String seatCode = seat.getSeatCode();
                boolean booked = seat.isBooked();
                double priceMulti = seat.getPriceMulti();
                String flightID = seat.getFlightID();
                statement.setLong(1, airPlaneID);
                statement.setString(2, flightID);
                statement.setString(3, seatCode);
                statement.setBoolean(4, booked);
                statement.setDouble(5, priceMulti);
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
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Seat> getSeatListByFlightID(long airplaneID) {
        return null;
    }
}
