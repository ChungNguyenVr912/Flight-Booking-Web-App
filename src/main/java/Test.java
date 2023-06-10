import dao.AirPlaneDAO;
import dao.FlightDAO;
import dao.SeatDAO;
import dao.UserDAO;
import model.Flight;
import model.abstraction.AirPlane;
import model.abstraction.Seat;
import model.abstraction.User;
import model.factory.AirPlaneFactory;
import model.impl.Customer;
import utils.Randomise;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;


public class Test {
    private static List<AirPlane> airPlanes = Randomise.getAirPlaneList();
    private static final TreeMap<Long, AirPlane> airPlaneTreeMap = new TreeMap<>();

    static {
        airPlanes.forEach(airPlane -> {
            airPlaneTreeMap.put(airPlane.getId(), airPlane);
        });
    }

    public static void main(String[] args) {
//        List<AirPlane> airPlanes = Randomise.randomPlaneList(200);
//        AirPlaneDAO.insertPlaneList(airPlanes);

//        List<AirPlane> airPlanes = AirPlaneDAO.selectAllPlane();
//        List<Seat> seatList = new ArrayList<>();
//        AirPlaneDAO.insertSeatList(seatList);
        LocalDateTime start = LocalDateTime.now();
        System.out.println("Start: " + start);
        List<Flight> flights = Randomise.randomListFlights(1000);
        List<Seat> seatList = new ArrayList<>();
        flights.forEach(flight ->
                seatList.addAll(AirPlaneFactory
                        .buildSeatList(getAirplane(flight.getAirPlaneID()), flight.getId())));
//        FlightDAO.insertFlight(flights);
//        SeatDAO.insertSeatList(seatList);
        System.out.println("finally......................................");
        LocalDateTime end = LocalDateTime.now();
        Duration duration = Duration.between(start, end);
        System.out.println(duration);
        System.out.println("Execute time: " + duration.toMinutes() + ":" + duration.toSeconds());
    }


    private static AirPlane getAirplane(long airplaneID) {
        return airPlaneTreeMap.get(airplaneID);
    }
}

