package utils;

import dto.FlightCardDTO;

import java.util.Comparator;

public class FlightDepartComparator implements Comparator<FlightCardDTO> {
    private static final FlightDepartComparator instance = new FlightDepartComparator();
    public static FlightDepartComparator getInstance() {
        return instance;
    }
    private FlightDepartComparator() {}
    @Override
    public int compare(FlightCardDTO flight1, FlightCardDTO flight2) {
        return flight1.getSortDepartTime().compareTo(flight2.getSortDepartTime());
    }
}
