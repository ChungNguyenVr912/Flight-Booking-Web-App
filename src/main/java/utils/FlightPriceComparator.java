package utils;

import model.Flight;

import java.util.Comparator;

public class FlightPriceComparator implements Comparator<Flight> {
    @Override
    public int compare(Flight flight1, Flight flight2) {
        return (int)(flight1.getBasePrice() - flight2.getBasePrice());
    }
}
