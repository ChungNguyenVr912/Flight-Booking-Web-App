package controller;

import dto.FlightDTO;
import dto.TicketDTO;
import model.Ticket;
import model.abstraction.User;
import service.FlightService;
import service.TicketService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "ticket_control", urlPatterns = {"/ticket"})
public class TicketServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            if (action == null) action = "";
            switch (action){
                case "seatAndBaggageConfirm" -> {
                    long seatID = Long.parseLong(request.getParameter("selectedSeat"));
                    if (!FlightService.checkSeat(seatID))throw new ServletException();
                    seatConfirmed(request,response);
                }
                case "passengerInfoSubmit" -> {
                    long seatID = ((Ticket)request.getSession().getAttribute("ticket")).getSeatID();
                    if (!FlightService.checkSeat(seatID))throw new ServletException();
                    updateTicket(request, response);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            request.getRequestDispatcher("WEB-INF/view/404_page.jsp").forward(request,response);
        }
    }

    private void updateTicket(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String passengerName = request.getParameter("firstname") + " " + request.getParameter("lastname");
        String passengerID = request.getParameter("idOrPassport");
        String passengerGender = request.getParameter("gender");
        String contact = request.getParameter("contactEmail");
        Ticket ticket = (Ticket) request.getSession().getAttribute("ticket");
        ticket.setPassengerName(passengerName);
        ticket.setPassengerID(passengerID);
        ticket.setPassengerGender(passengerGender);
        ticket.setContact(contact);
        int luggageID = (int)request.getSession().getAttribute("luggageID");
        TicketService.updateTicketLuggageFlight(ticket, luggageID);
        FlightDTO flightDTO = (FlightDTO) request.getSession().getAttribute("flightDTO");
        request.getSession().removeAttribute("flightDTO");
        request.getSession().removeAttribute("departure");
        request.getSession().removeAttribute("destination");
        TicketDTO ticketDTO = TicketService.getTicketDTO(ticket,flightDTO);
        request.setAttribute("ticketDTO",ticketDTO);
        request.getRequestDispatcher("ticketQR.jsp").forward(request,response);
    }

    private void seatConfirmed(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = UUID.randomUUID().toString();
        long userID = -1;
        User user = (User) request.getSession().getAttribute("user");
        if (user != null){
            userID = user.getId();
        }
        String flightID = ((FlightDTO) request.getSession().getAttribute("flightDTO")).getId();
        long seatID = Long.parseLong(request.getParameter("selectedSeat"));
        Ticket ticket = Ticket.builder()
                .id(id)
                .flightID(flightID)
                .userID(userID)
                .seatID(seatID)
                .build();
        int selectedLuggage = Integer.parseInt(request.getParameter("luggageID"));
        request.getSession().setAttribute("ticket",ticket);
        request.getSession().setAttribute("luggageID",selectedLuggage);
        request.getRequestDispatcher("passenger_info.jsp").forward(request,response);
    }
}
