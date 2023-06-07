package utils;

import dto.FlightCardDTO;

import java.util.Comparator;

public class FlightPriceComparator implements Comparator<FlightCardDTO> {
    private static final FlightPriceComparator instance = new FlightPriceComparator();
    public static FlightPriceComparator getInstance() {
        return instance;
    }
    private FlightPriceComparator() {}
    @Override
    public int compare(FlightCardDTO flight1, FlightCardDTO flight2) {
        return (int) (flight1.getSortBasePrice() - flight2.getSortBasePrice());
    }
}
