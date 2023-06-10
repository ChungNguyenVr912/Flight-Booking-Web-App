package dao;

import connection.JDBCConnection;
import dto.FlightCardDTO;
import model.Flight;
import utils.FlightPriceComparator;
import utils.MyDateTime;
import utils.Randomise;

import java.sql.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightDAO {
    private static final String INSERT_FLIGHT_SQL =
            "INSERT INTO flight(id, flight_code, airlines_ID, airplane_ID, departure, destination, depart_time, arrival_time, base_price) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String SELECT_FLIGHT_SQL =
            "select * from flight where departure = ? and destination = ?";
    private static final String SELECT_FLIGHT_BY_ID =
            "select * from flight where id = ?";

    private static final String GET_FLIGHT_CARD_DETAIL_SQL
            = "SELECT fl.id, fl.flight_code, al.name AS airlines_name, al.logo_img_url, ap.name AS airplane_name " +
            ", ap.type AS airplane_type, fl.departure, fl.destination, fl.depart_time, fl.arrival_time, fl.base_price " +
            "FROM flight fl " +
            "JOIN airplane ap ON ap.id = fl.airplane_ID " +
            "JOIN airlines_company al ON al.airlines_account_id = fl.airlines_ID " +
            "WHERE departure = ? AND destination = ? ;";
    private static final String GET_FLIGHT_CARD_BY_FLIGHTID
            = "SELECT fl.id, fl.flight_code, al.name AS airlines_name, al.logo_img_url, ap.name AS airplane_name " +
            ", ap.type AS airplane_type, fl.departure, fl.destination, fl.depart_time, fl.arrival_time, fl.base_price " +
            "FROM flight fl " +
            "JOIN airplane ap ON ap.id = fl.airplane_ID " +
            "JOIN airlines_company al ON al.airlines_account_id = fl.airlines_ID " +
            "WHERE fl.id = ? ;";

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

    public static List<FlightCardDTO> getFlightCardDetail(String departure, String destination) {
        List<FlightCardDTO> flightCardDTOList = new ArrayList<>();
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_FLIGHT_CARD_DETAIL_SQL)) {
            statement.setString(1, departure);
            statement.setString(2, destination);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                LocalDateTime depart = resultSet.getTimestamp("depart_time").toLocalDateTime();
                String departTime = MyDateTime.toHourAndMinute(depart);
                String arrivalTime = MyDateTime.toHourAndMinute(resultSet.getTimestamp("arrival_time").toLocalDateTime());
                int duration = Randomise.getFlightDuration(departure, destination);
                String flightTime = duration / 60 + ":" + duration % 60;
                String departDate = MyDateTime.toDayMonthYear(depart);
                long basePrice = (long)resultSet.getDouble("base_price");
                String type = resultSet.getString("airplane_type");
                LocalDateTime now = LocalDateTime.now();
                if (depart.isAfter(now)) {
                    flightCardDTOList.add(FlightCardDTO.builder()
                            .id(resultSet.getString("id"))
                            .flightCode(resultSet.getString("flight_code"))
                            .airlinesName(resultSet.getString("airlines_name"))
                            .airlinesImgUrl(resultSet.getString("logo_img_url"))
                            .airplaneName(resultSet.getString("airplane_name"))
                            .withBusinessClass(type.equals("business"))
                            .departure(departure)
                            .destination(destination)
                            .departDate(departDate)
                            .sortDepartTime(depart)
                            .departTime(departTime)
                            .arrivalTime(arrivalTime)
                            .flightTime(flightTime)
                            .basePrice((new DecimalFormat("VND #,###,###"))
                                    .format(basePrice - (basePrice%5000)))
                            .sortBasePrice(basePrice)
                            .build());
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        flightCardDTOList.sort(FlightPriceComparator.getInstance());
        return flightCardDTOList;
    }

    public static FlightCardDTO getFlightCardDTO(String flightID){
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_FLIGHT_CARD_BY_FLIGHTID)) {
            statement.setString(1, flightID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                LocalDateTime depart = resultSet.getTimestamp("depart_time").toLocalDateTime();
                String departTime = MyDateTime.toHourAndMinute(depart);
                String arrivalTime = MyDateTime.toHourAndMinute(resultSet.getTimestamp("arrival_time").toLocalDateTime());
                String departure = resultSet.getString("departure");
                String destination = resultSet.getString("destination");
                int duration = Randomise.getFlightDuration(departure, destination);
                String flightTime = duration / 60 + ":" + duration % 60;
                String departDate = MyDateTime.toDayMonthYear(depart);
                long basePrice = (long)resultSet.getDouble("base_price");
                String type = resultSet.getString("airplane_type");
                LocalDateTime now = LocalDateTime.now();
                if (depart.isAfter(now)) {
                    return FlightCardDTO.builder()
                            .id(resultSet.getString("id"))
                            .flightCode(resultSet.getString("flight_code"))
                            .airlinesName(resultSet.getString("airlines_name"))
                            .airlinesImgUrl(resultSet.getString("logo_img_url"))
                            .airplaneName(resultSet.getString("airplane_name"))
                            .withBusinessClass(type.equals("business"))
                            .departure(departure)
                            .destination(destination)
                            .departDate(departDate)
                            .sortDepartTime(depart)
                            .departTime(departTime)
                            .arrivalTime(arrivalTime)
                            .flightTime(flightTime)
                            .basePrice((new DecimalFormat("VND #,###,###"))
                                    .format(basePrice - (basePrice%5000)))
                            .sortBasePrice(basePrice)
                            .build();
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Flight getFlight(String flightID) {
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_FLIGHT_BY_ID)) {
            statement.setString(1, flightID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Flight.builder()
                        .id(flightID)
                        .flightCode(resultSet.getString("flight_code"))
                        .airLinesID(resultSet.getLong("airlines_ID"))
                        .airPlaneID(resultSet.getLong("airplane_ID"))
                        .departure(resultSet.getString("departure"))
                        .destination(resultSet.getString("destination"))
                        .departTime(resultSet.getTimestamp("depart_time").toLocalDateTime())
                        .arrivalTime(resultSet.getTimestamp("arrival_time").toLocalDateTime())
                        .basePrice(resultSet.getDouble("base_price"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Flight.builder().build();
    }
}
