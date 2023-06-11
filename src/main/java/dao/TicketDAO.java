package dao;

import connection.JDBCConnection;
import model.Ticket;
import service.TicketService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {
    private static final String INSERT_TICKET_SQL
            = "insert into ticket (id, user_id, flight_id, seat_id, passenger_name, passengerID, passenger_gender, contact) VALUES (?,?,?,?,?,?,?,?)";
    private static final String INSERT_LUGGAGE_SQL =
            "insert into luggage_detail (luggage_id, ticket_id) VALUES (?,?)";
    private static final String SELECT_TICKET_BY_USER = "";
    private static final String SELECT_LUGGAGE_SQL = "select * from luggage where id = ?";

    public static void insertTicket(Ticket ticket){
        try (Connection connection = JDBCConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(INSERT_TICKET_SQL)){
            statement.setString(1, ticket.getId());
            statement.setLong(2,ticket.getUserID());
            statement.setString(3, ticket.getFlightID());
            statement.setLong(4, ticket.getSeatID());
            statement.setString(5, ticket.getPassengerName());
            statement.setString(6, ticket.getPassengerID());
            statement.setString(7, ticket.getPassengerGender());
            statement.setString(8, ticket.getContact());
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void insertLuggage(String ticketID, int luggageID){
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_LUGGAGE_SQL)){
            statement.setString(2,ticketID);
            statement.setInt(1, luggageID);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static List<Ticket> selectTicket(long userID){
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_TICKET_BY_USER)){
            statement.setLong(1, userID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                tickets.add(Ticket.builder()
                        .id(resultSet.getString("id"))
                        .userID(userID)
                        .flightID(resultSet.getString("flight_id"))
                        .seatID(resultSet.getLong("seat_id"))
                        .passengerName(resultSet.getString("passenger_name"))
                        .passengerID(resultSet.getString("passengerID"))
                        .passengerGender(resultSet.getString("passenger_gender"))
                        .contact(resultSet.getString("contact"))
                        .status(resultSet.getString("status"))
                        .build());
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return tickets;
    }

    public static String selectLuggage(int luggageID){
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_LUGGAGE_SQL)){
            statement.setInt(1,luggageID);
            return statement.executeQuery().getString("type");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
