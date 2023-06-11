package utils;

import dto.FlightDTO;

import java.util.Comparator;

public class FlightDepartComparator implements Comparator<FlightDTO> {
    private static final FlightDepartComparator instance = new FlightDepartComparator();
    public static FlightDepartComparator getInstance() {
        return instance;
    }
    private FlightDepartComparator() {}
    @Override
    public int compare(FlightDTO flight1, FlightDTO flight2) {
        return flight1.getSortDepartTime().compareTo(flight2.getSortDepartTime());
    }
}
