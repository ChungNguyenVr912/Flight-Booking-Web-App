package utils;

import dto.FlightDTO;

import java.util.Comparator;

public class FlightPriceComparator implements Comparator<FlightDTO> {
    private static final FlightPriceComparator instance = new FlightPriceComparator();
    public static FlightPriceComparator getInstance() {
        return instance;
    }
    private FlightPriceComparator() {}
    @Override
    public int compare(FlightDTO flight1, FlightDTO flight2) {
        return (int) (flight1.getSortBasePrice() - flight2.getSortBasePrice());
    }
}
