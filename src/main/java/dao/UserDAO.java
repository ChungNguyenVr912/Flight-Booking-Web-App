package dao;

import connection.JDBCConnection;
import lombok.NoArgsConstructor;
import model.abstraction.User;
import model.impl.*;
import org.mindrot.jbcrypt.BCrypt;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class UserDAO {
    private static final String INSERT_USER_SQL
            = "INSERT INTO user (username, email, password, full_name,gender, role) VALUES (?,?,?,?,?,?)";
    private static final String SELECT_USER_SQL
            = "select * from user where username = ?";
    private static final String SELECT_ALL_AIRLINES_SQL
            = "select airlines_account_id,name, logo_img_url, price_multi from airlines_company";
    private static final String SELECT_USER_BY_EMAIL_SQL
            = "select username from user where email = ?";
    private static final String UPDATE_USER_SQL
            = "UPDATE user SET password = ?, full_name = ?, gender = ?, day_of_birth = ?, phone_number = ? WHERE id = ?";

    public static List<AirlinesCompany> getAllAirlines() {
        List<AirlinesCompany> airlinesList = new ArrayList<>();
        try (Connection connection = JDBCConnection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_AIRLINES_SQL);
            while (resultSet.next()) {
                airlinesList.add(AirlinesCompany.builder()
                        .id(resultSet.getInt("airlines_account_id"))
                        .name(resultSet.getString("name"))
                        .logoImgUrl(resultSet.getString("logo_img_url"))
                        .priceMulti(resultSet.getDouble("price_multi"))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return airlinesList;
    }

    public void insertUser(User user) {
        String role = "unknown";
        if (user instanceof Customer) {
            role = "customer";
        } else if (user instanceof Staff) {
            role = "staff";
        } else if (user instanceof Airlines) {
            role = "airlines";
        }
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_USER_SQL)) {
            String hashedPasword = BCrypt.hashpw(user.getPassWord(), BCrypt.gensalt(10, SecureRandom.getInstanceStrong()));
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashedPasword);
            statement.setString(4, user.getFullName());
            if (user instanceof Customer) {
                statement.setString(5, ((Customer) user).getGender());
            }
            statement.setString(6, role);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Fail!");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUser(String username) {
        User user = null;
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_SQL)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String role = resultSet.getString("role");
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String fullName = resultSet.getString("full_name");
                String gender = resultSet.getString("gender");
                LocalDate dob = null;
                LocalDate applyDay = null;
                Date date = resultSet.getDate("day_of_birth");
                if (date != null) {
                    dob = date.toLocalDate();
                }
                date = resultSet.getDate("apply_day");
                if (date != null) {
                    applyDay = date.toLocalDate();
                }
                String phoneNumber = resultSet.getString("phone_number");
                switch (role) {
                    case "admin" -> user = Admin.builder()
                            .id(id)
                            .userName(username)
                            .passWord(password)
                            .fullName(fullName)
                            .build();
                    case "customer" -> user = Customer.builder()
                            .id(id)
                            .userName(username)
                            .email(email)
                            .passWord(password)
                            .fullName(fullName)
                            .phoneNumber(phoneNumber)
                            .dayOfBirth(dob)
                            .gender(gender)
                            .build();
                    case "staff" -> user = Staff.builder()
                            .id(id)
                            .userName(username)
                            .email(email)
                            .passWord(password)
                            .fullName(fullName)
                            .gender(gender)
                            .phoneNumber(phoneNumber)
                            .applyDay(applyDay)
                            .build();
                    case "airlines" -> user = Airlines.builder()
                            .id(id)
                            .userName(username)
                            .email(email)
                            .passWord(password)
                            .fullName(fullName)
                            .build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void updateUser(int userID) {

    }

    public String getUsernameByEmail(String email) {
        String username = null;
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL_SQL)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                username = resultSet.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return username;
    }

    public boolean checkUsername(String username) {
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_SQL)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkEmail(String email) {
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL_SQL)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}