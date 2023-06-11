package utils;

import dto.FlightDTO;


import java.util.Comparator;

public class FlightClassComparator implements Comparator<FlightDTO> {
    private static final FlightClassComparator instance = new FlightClassComparator();
    public static FlightClassComparator getInstance() {
        return instance;
    }
    private FlightClassComparator() {}

    @Override
    public int compare(FlightDTO flight1, FlightDTO flight2) {
        int n1 = flight1.isWithBusinessClass() ? 1 : 0;
        int n2 = flight2.isWithBusinessClass() ? 1 : 0;
        return n2 - n1;
    }
}
