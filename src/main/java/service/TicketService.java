package service;

import dao.AirPortDAO;
import dao.SeatDAO;
import dao.TicketDAO;
import dto.FlightDTO;
import dto.TicketDTO;
import model.Ticket;
import model.abstraction.Seat;

import java.util.HashMap;

public class TicketService {
    public static void updateTicketLuggageFlight(Ticket ticket, int luggageID){
        TicketDAO.insertTicket(ticket);
        TicketDAO.insertLuggage(ticket.getId(), luggageID);
        SeatDAO.updateSeatStatus(ticket.getSeatID());
    }

    public static TicketDTO getTicketDTO(String ticketiID) {
        return null;
    }

    public static TicketDTO getTicketDTO(Ticket ticket, FlightDTO flightDTO) {
        Seat seat = SeatDAO.selectSeat(ticket.getSeatID());
        String seatCode = seat!=null? seat.getSeatCode() : "null";
        String seatClass = seat!=null? seat.getType() : "null";
        HashMap<String,String> airportList = AirPortDAO.getAirPortName();
        return TicketDTO.builder()
                .flightCode(flightDTO.getFlightCode())
                .airlines(flightDTO.getAirlinesName())
                .airlinesImg(flightDTO.getAirlinesImgUrl())
                .departureCode(flightDTO.getDeparture())
                .departure(airportList.get(flightDTO.getDeparture()))
                .destinationCode(flightDTO.getDestination())
                .destination(airportList.get(flightDTO.getDestination()))
                .departTime(flightDTO.getDepartTime())
                .arrivalTime(flightDTO.getArrivalTime())
                .flyTime(flightDTO.getFlightTime())
                .seatCode(seatCode)
                .seatClass(seatClass)
                .passengerName(ticket.getPassengerName())
                .passengerGender(ticket.getPassengerGender())
                .build();
    }
}
