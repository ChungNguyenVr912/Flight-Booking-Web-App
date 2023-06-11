package service;


import dao.AirPlaneDAO;
import dao.FlightDAO;
import dao.SeatDAO;
import dto.SeatDTO;
import model.abstraction.Seat;

import java.util.ArrayList;
import java.util.List;

public class FlightService {

    public static List<List<SeatDTO>> getListSeatDTO(String flightID){
        List<SeatDTO> seatDTOList = new ArrayList<>();
        List<Seat> seatList = SeatDAO.selectSeatList(flightID);
        String airplaneName = AirPlaneDAO
                .getAirplane(seatList.get(0).getAirPlaneID()).getName();
        double basePrice = FlightDAO.getFlight(flightID).getBasePrice();
        for (Seat seat : seatList) {
            double price = basePrice * seat.getPriceMulti();
            price = price - (price % 5000);
            seatDTOList.add(SeatDTO.builder()
                    .id(seat.getId())
                    .seatCode(seat.getSeatCode())
                    .airplaneName(airplaneName)
                    .booked(seat.isBooked())
                    .price(price)
                    .type(seat.getType())
                    .build());
        }
        List<List<SeatDTO>> list = new ArrayList<>();
        int i = 0;
        List<SeatDTO> subList = new ArrayList<>();
        for (SeatDTO seat : seatDTOList) {
            if (i==0){
                subList = new ArrayList<>();
            }
            if (i<4){
                subList.add(seat);
                if (i==3){
                    list.add(subList);
                    i=0;
                    continue;
                }
            }
            i++;
        }
        return list;
    }


    public static boolean checkSeat(long seatID){
        Seat seat = SeatDAO.selectSeat(seatID);
        if (seat!=null){
            return !seat.isBooked();
        }else {
            return false;
        }
    }
}
