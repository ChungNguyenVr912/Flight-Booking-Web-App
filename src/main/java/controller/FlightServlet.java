package controller;

import dao.FlightDAO;
import model.Flight;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "flight_controller", value = "/flight_controller")
public class FlightServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "";
        switch (action){
            case "show_flights" -> searchFlight(request, response);
        }
    }

    private void searchFlight(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String departure = request.getParameter("departure");
        String destination = request.getParameter("destination");
        String departString = request.getParameter("departDay");
        LocalDate departDay = null;
        if (departString != null){
            departDay = LocalDate.parse(departString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        List<Flight> flights = FlightDAO.selectFlight(departure,destination);
        List<Flight> filteredFlights = new ArrayList<>();
        if (departDay!=null){
            for (Flight flight : flights) {
                if (flight.getDepartTime().toLocalDate().equals(departDay)){
                    filteredFlights.add(flight);
                }
            }
        }
    }
}
