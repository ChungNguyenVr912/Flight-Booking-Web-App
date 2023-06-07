package controller;

import dao.AirPortDAO;
import dao.UserDAO;
import model.abstraction.User;
import model.impl.Customer;
import org.mindrot.jbcrypt.BCrypt;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "flightBooking", urlPatterns = {"/home","/"})
public class AccountServlet extends HttpServlet {
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "logout" -> {
                request.getSession().setAttribute("user", null);
                response.sendRedirect(request.getContextPath() + "/home");
            }
            default -> {
                HashMap<String,String> airports = AirPortDAO.getAirPortName();
                request.getSession().setAttribute("airports", airports);
                RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
                dispatcher.forward(request, response);
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
            case "create_account" -> createAccount(request, response);
            case "check_username" -> {
                String userNameFeedback;
                if (checkUsername(request)) {
                    userNameFeedback = "invalid";
                } else {
                    userNameFeedback = "valid";
                }
                response.setContentType("text/plain");
                response.getWriter().write(userNameFeedback);
            }
            case "check_email" -> {
                String emailFeedback;
                if (checkEmail(request)) {
                    emailFeedback = "invalid";
                } else {
                    emailFeedback = "valid";
                }
                response.setContentType("text/plain");
                response.getWriter().write(emailFeedback);
            }
            case "login" -> login(request, response);
        }
    }

    private boolean checkUsername(HttpServletRequest request) {
        String username = request.getParameter("username");
        if (username != null) {
            return userDAO.checkUsername(username) && username.length() >= 4;
        } else {
            return false;
        }
    }

    private boolean checkEmail(HttpServletRequest request) {
        String email = request.getParameter("email");
        if (email != null) {
            return userDAO.checkEmail(email);
        } else {
            return false;
        }
    }
    private void createAccount(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String gender = request.getParameter("gender");
            User newUser = Customer.builder()
                    .userName(username)
                    .email(email)
                    .passWord(password)
                    .gender(gender)
                    .build();
            userDAO.insertUser(newUser);
            response.sendRedirect(request.getContextPath() + "/home");
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String usernameOrEmail = request.getParameter("loginUsernameOrEmail");
        String password = request.getParameter("loginPassword");
        User user;
        if (userDAO.checkUsername(usernameOrEmail)) {
            user = userDAO.getUser(usernameOrEmail);
        } else {
            String username = userDAO.getUsernameByEmail(usernameOrEmail);
            user = userDAO.getUser(username);
        }
        if (user != null) {
            if (BCrypt.checkpw(password, user.getPassWord())) {
                request.getSession().setAttribute("user", user);
            }
        }
        String redirectUrl = request.getContextPath() + "/home";
        response.sendRedirect(redirectUrl);
    }


    public void destroy() {
    }
}