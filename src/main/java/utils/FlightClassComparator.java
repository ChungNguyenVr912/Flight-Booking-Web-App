package utils;

import dto.FlightCardDTO;


import java.util.Comparator;

public class FlightClassComparator implements Comparator<FlightCardDTO> {
    private static final FlightClassComparator instance = new FlightClassComparator();
    public static FlightClassComparator getInstance() {
        return instance;
    }
    private FlightClassComparator() {}

    @Override
    public int compare(FlightCardDTO flight1, FlightCardDTO flight2) {
        int n1 = flight1.isWithBusinessClass() ? 1 : 0;
        int n2 = flight2.isWithBusinessClass() ? 1 : 0;
        return n2 - n1;
    }
}
