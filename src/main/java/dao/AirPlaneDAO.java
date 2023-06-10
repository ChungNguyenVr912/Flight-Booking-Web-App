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
    private static final String SELECT_PLANE_SQL
            = "select * from airplane where id = ?";

    private static final String INSERT_PLANE_SQL
            = "insert into airplane(name, type) VALUES (?,?)";

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
    public static AirPlane getAirplane(long id){
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_PLANE_SQL)) {
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                String type = resultSet.getString("type");
                switch (type){
                    case "business" -> {
                        return BusinessAirplane.builder()
                                .id(id).name(resultSet.getString("name")).build();
                    }
                    case "economy" -> {
                        return EconomyAirplane.builder()
                                .id(id).name(resultSet.getString("name")).build();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return EconomyAirplane.builder().build();
    }
}
