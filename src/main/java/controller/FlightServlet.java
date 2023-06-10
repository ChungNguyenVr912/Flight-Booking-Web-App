package controller;

import dao.AirPortDAO;
import dao.FlightDAO;
import dto.FlightCardDTO;
import dto.SeatDTO;
import service.FlightService;
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
import java.util.HashMap;
import java.util.List;


@WebServlet(name = "flight_controller", value = "/flight")
public class FlightServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "";
        switch (action) {
            case "sort" -> sortFlight(request, response);
            default -> request.getRequestDispatcher("home.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            if (action == null) action = "";
            switch (action) {
                case "show_flights" -> showFlights(request, response);
                case "selectFlight" -> doFlightSelected(request, response);
            }
        } catch (ServletException | IOException e) {
            e.printStackTrace();
            request.getRequestDispatcher("WEB-INF/view/404_page.jsp").forward(request, response);
        }

    }

    private void doFlightSelected(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String flightID = request.getParameter("flightID");
        List<List<SeatDTO>> seatDTOList = FlightService.getListSeatDTO(flightID);
        FlightCardDTO flightDTO = FlightDAO.getFlightCardDTO(flightID);
        HashMap<String,String> airportList = AirPortDAO.getAirPortName();
        List<String> airportName = new ArrayList<>();
        airportName.add(airportList.get((String)request.getSession().getAttribute("departure")));
        airportName.add(airportList.get((String)request.getSession().getAttribute("destination")));
        request.setAttribute("airportName",airportName);
        request.setAttribute("flightDTO", flightDTO);
        request.setAttribute("seatDtoList", seatDTOList);
        request.getRequestDispatcher("seat_select.jsp").forward(request, response);
    }

    private void showFlights(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String departure = request.getParameter("departure");
        String destination = request.getParameter("destination");
        String departString = request.getParameter("departDay");
        LocalDate departDay = null;
        if (!departString.equals("")) {
            try {
                departDay = LocalDate.parse(departString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (Exception e) {
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
        request.getSession().setAttribute("departure", departure);
        request.getSession().setAttribute("destination", destination);
        request.getSession().setAttribute("flightCards", flights);
        request.getRequestDispatcher("search_flight.jsp").forward(request, response);
    }

    private void sortFlight(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sortType = request.getParameter("sortType");
        List<FlightCardDTO> list = (List<FlightCardDTO>) request.getSession().getAttribute("flightCards");
        switch (sortType) {
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
