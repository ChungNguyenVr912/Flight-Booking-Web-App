package utils;

import model.Flight;

import java.util.Comparator;

public class FlightDepartComparator implements Comparator<Flight> {
    @Override
    public int compare(Flight flight1, Flight flight2) {
        return flight1.getDepartTime().compareTo(flight2.getDepartTime());
    }
}
