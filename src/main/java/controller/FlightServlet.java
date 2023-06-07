package controller;

import dao.FlightDAO;
import dto.FlightCardDTO;
import model.Flight;
import utils.FlightClassComparator;
import utils.FlightDepartComparator;
import utils.FlightPriceComparator;

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


@WebServlet(name = "flight_controller", value = "/flight")
public class FlightServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "";
        switch (action){
            case "sort" ->  sortFlight(request, response);
            default -> request.getRequestDispatcher("home.jsp").forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "";
        switch (action) {
            case "show_flights" -> searchFlight(request, response);
        }
    }

    private void searchFlight(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String departure = request.getParameter("departure");
        String destination = request.getParameter("destination");
        String departString = request.getParameter("departDay");
        LocalDate departDay = null;
        if (!departString.equals("")) {
            try {
                departDay = LocalDate.parse(departString, DateTimeFormatter.ofPattern("yyyy-dd-MM"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        List<FlightCardDTO> flights = FlightDAO.getFlightCardDetail(departure, destination);
        if (departDay != null) {
            List<FlightCardDTO> filteredFlights = new ArrayList<>();
            for (FlightCardDTO flight : flights) {
                if (flight.getSortDepartTime().toLocalDate().equals(departDay)) {
                    filteredFlights.add(flight);
                }
            }
            flights = new ArrayList<>(filteredFlights);
        }
        request.getSession().setAttribute("flightCards", flights);
        request.getRequestDispatcher("search_flight.jsp").forward(request, response);
    }

    private void sortFlight(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sortType = request.getParameter("sortType");
        List<FlightCardDTO> list = (List<FlightCardDTO>)request.getSession().getAttribute("flightCards");
        switch (sortType){
            case "best" -> {
                list.sort(FlightPriceComparator.getInstance().reversed());
                list.sort(FlightClassComparator.getInstance());
            }
            case "cheapest" -> list.sort(FlightPriceComparator.getInstance());
            case "earliest" -> list.sort(FlightDepartComparator.getInstance());
        }
        request.getSession().setAttribute("flightCards", list);
        request.getRequestDispatcher("search_flight.jsp").forward(request, response);
    }
}
