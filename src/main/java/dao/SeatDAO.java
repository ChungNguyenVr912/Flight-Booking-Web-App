package dao;

import connection.JDBCConnection;
import model.abstraction.Seat;
import model.impl.BusinessSeat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatDAO {
    private static final String INSERT_SEAT_SQL
            = "insert into seat(airplane_id,flight_id, seat_code, booked, price_multi,type) VALUES (?,?,?,?,?,?)";
    private static final String SELECT_SEAT_LIST_SQL = "select * from seat where flight_id = ?";
    private static final String UPDATE_SEAT_SQL = "update seat set booked = true where id = ?";
    private static final String SELECT_SEAT_SQL = "select * from seat where id = ?";

    public static List<Seat> selectSeatList(String flightID) {
        List<Seat> seatList = new ArrayList<>();
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_SEAT_LIST_SQL)) {
            statement.setString(1, flightID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                seatList.add(Seat.builder()
                        .id(resultSet.getLong("id"))
                        .airPlaneID(resultSet.getLong("airplane_id"))
                        .flightID(resultSet.getString("flight_id"))
                        .seatCode(resultSet.getString("seat_code"))
                        .booked(resultSet.getBoolean("booked"))
                        .priceMulti(resultSet.getDouble("price_multi"))
                        .type(resultSet.getString("type"))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seatList;
    }

    public static Seat selectSeat(long seatID){
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_SEAT_SQL)){
            statement.setLong(1, seatID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return Seat.builder()
                        .id(seatID)
                        .airPlaneID(resultSet.getLong("airplane_id"))
                        .flightID(resultSet.getString("flight_id"))
                        .seatCode(resultSet.getString("seat_code"))
                        .booked(resultSet.getBoolean("booked"))
                        .priceMulti(resultSet.getDouble("price_multi"))
                        .type(resultSet.getString("type"))
                        .build();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void updateSeatStatus(long seatID){
        try (Connection connection = JDBCConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE_SEAT_SQL)){
            statement.setLong(1, seatID);
            statement.executeUpdate();
        }catch (SQLException e){
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
                String type = (seat instanceof BusinessSeat) ? "business" : "economy";
                statement.setLong(1, airPlaneID);
                statement.setString(2, flightID);
                statement.setString(3, seatCode);
                statement.setBoolean(4, booked);
                statement.setDouble(5, priceMulti);
                statement.setString(6, type);
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
}
