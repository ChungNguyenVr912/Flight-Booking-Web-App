package model.factory;

import model.abstraction.AirPlane;
import model.abstraction.Seat;
import model.impl.BusinessAirplane;
import model.impl.BusinessSeat;
import model.impl.EconomyAirplane;
import model.impl.EconomySeat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AirPlaneFactory {
    private static final ArrayList<String> listPlaneName;
    private static final String[] prefixSeatCode = {"A", "B", "C", "D"};
    private static final Random random = new Random();

    static {
        String[] planeList = {"AirFish 330", "AirPenguin 1235", "AirOtter 113", "AirDog 114", "AirMeow 115", "AirDuck 911"};
        listPlaneName = new ArrayList<>(List.of(planeList));
    }

    public static AirPlane getAirplane(String type) {
        String airPlaneName = listPlaneName.get(random.nextInt(listPlaneName.size()));
        switch (type) {
            case "economy" -> {
                return EconomyAirplane.builder()
                        .name(airPlaneName)
                        .build();
            }
            case "business" -> {
                return BusinessAirplane.builder()
                        .name(airPlaneName)
                        .build();
            }
            default -> {
                return null;
            }
        }
    }

    public static List<Seat> buildSeatList(AirPlane airPlane, String flightID) {
        List<Seat> seatList = new ArrayList<>();
        if (airPlane instanceof EconomyAirplane) {
            for (int suffix = 1; suffix < 16; suffix++) {
                for (int prefix = 0; prefix < 4; prefix++) {
                    seatList.add(EconomySeat.builder()
                            .airPlaneID(airPlane.getId())
                            .flightID(flightID)
                            .seatCode(prefixSeatCode[prefix] + suffix)
                            .booked(false)
                            .priceMulti(1)
                            .build());
                }
            }
        } else {
            for (int suffix = 1; suffix < 6; suffix++) {
                for (int prefix = 0; prefix < 4; prefix++) {
                    seatList.add(BusinessSeat.builder()
                            .priceMulti(1.8)
                            .flightID(flightID)
                            .booked(false)
                            .airPlaneID(airPlane.getId())
                            .seatCode(prefixSeatCode[prefix] + suffix)
                            .build());
                }
            }
            for (int suffix = 6; suffix < 11; suffix++) {
                for (int prefix = 0; prefix < 4; prefix++) {
                    seatList.add(EconomySeat.builder()
                            .airPlaneID(airPlane.getId())
                            .flightID(flightID)
                            .seatCode(prefixSeatCode[prefix] + suffix)
                            .booked(false)
                            .priceMulti(1)
                            .build());
                }
            }
        }
        return seatList;
    }
}
