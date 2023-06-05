package utils;

import dao.AirPlaneDAO;
import dao.AirPortDAO;
import dao.UserDAO;
import lombok.Getter;
import model.Flight;
import model.abstraction.AirPlane;
import model.factory.AirPlaneFactory;
import model.impl.Airlines;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Randomise {
    private static final double baseCost = 500000;
    private static final double distanceMulti = 15000.0;
    private static final double dayFlightPriceMulti = 1.15;
    private static final Random random = new Random();
    private static final List<AirPlane> airPlaneList;
    private static final List<Airlines> airlinesList;
    //    private static final HashMap<String, String> airPorts;
    private static final HashMap<String, Integer> airPortUnrealDistance;

    static {
        airPlaneList = AirPlaneDAO.selectAllPlane();
        airPortUnrealDistance = AirPortDAO.getAirPortDistance();
        airlinesList = UserDAO.getAllAirlines();
    }

    public static List<AirPlane> getAirPlaneList() {
        return airPlaneList;
    }

    public static List<Flight> randomListFlights(int quantity) {
        Set<LocalDateTime> setOfDepartTime = new HashSet<>();
        while (setOfDepartTime.size() < quantity) {
            setOfDepartTime.add(randomDepartTimeInNextNumberOfDay(10));
        }
        List<Flight> flightList = new ArrayList<>();
        int FID = 1;
        for (LocalDateTime departTime : setOfDepartTime) {
            Airlines airlines = airlinesList.get(random.nextInt(airlinesList.size()));
            String airLinesName = airlines.getFullName();
            double priceMulti = airlines.getPriceMulti();
            String[] route = randomLocation();
            String departure = route[0];
            String destination = route[1];
            String flightCode = generateFlightCode(airLinesName, departTime, FID);
            String id = UUID.nameUUIDFromBytes(flightCode.getBytes()).toString();
            int flyTime = getFlightDuration(departure, destination);
            LocalDateTime arrivalTime = departTime.plusHours(flyTime / 60).plusMinutes(flyTime % 60);
            double basePrice = getBaseCost(departure, destination, departTime) * priceMulti;
            AirPlane airPlane = airPlaneList.get(random.nextInt(airPlaneList.size() - 1));
            flightList.add(Flight.builder().id(id)
                    .airLinesID(airlines.getId())
                    .airPlaneID(airPlane.getId())
                    .flightCode(flightCode)
                    .departure(departure).destination(destination)
                    .departTime(departTime).arrivalTime(arrivalTime)
                    .basePrice(basePrice).build());
            FID++;
        }
        return flightList;
    }

    private static int getFlightDuration(String departure, String destination) {
        final float timeMulti = 3.85f;
        final int baseFlyTime = 45;// in minute
        if (airPortUnrealDistance.containsKey(departure)
                && airPortUnrealDistance.containsKey(destination)) {
            int startPos = airPortUnrealDistance.get(departure);
            int endPos = airPortUnrealDistance.get(destination);
            int distance = Math.abs(startPos - endPos);
            return (int) (distance * timeMulti) - ((int) (distance * timeMulti)) % 5 + baseFlyTime;
        } else {
            return -1;
        }
    }

    public static String[] randomLocation() {
        ArrayList<String> arrLocation = new ArrayList<>(airPortUnrealDistance.keySet());
        String departure = arrLocation.get(random.nextInt(arrLocation.size() - 1));
        int startPos = airPortUnrealDistance.get(departure);
        arrLocation.remove(departure);
        ArrayList<String> ignoreLocation = new ArrayList<>();
        airPortUnrealDistance.forEach((key, value) -> {
            if (Math.abs(value - startPos) < 2) {
                ignoreLocation.add(key);
            }
        });
        ignoreLocation.forEach(arrLocation::remove);
        String destination = arrLocation.get(random.nextInt(arrLocation.size() - 1));
        return new String[]{departure, destination};
    }

    public static LocalDateTime randomDepartTimeInNextNumberOfDay(int days) {
        int hour = random.nextInt(24);
        int minute = random.nextInt(6) * 10;
        int day = random.nextInt(days) + 1;
        return LocalDateTime.now().withHour(hour).withMinute(minute).withSecond(0).plusDays(day);
    }

    public static List<AirPlane> randomPlaneList(int quantity) {
        List<AirPlane> planeList = new ArrayList<>();
        while (planeList.size() < quantity) {
            planeList.add(randomAirPlane());
        }
        return planeList;
    }

    private static AirPlane randomAirPlane() {
        int choice = random.nextInt(2);
        if (choice == 0) {
            return AirPlaneFactory.getAirplane("economy");
        } else {
            return AirPlaneFactory.getAirplane("business");
        }
    }

    private static AirPlane randomAirPlaneInPlaneList() {
        return Randomise.airPlaneList.get(random.nextInt(Randomise.airPlaneList.size() - 1));
    }

    private static String generateFlightCode(String airLinesName, LocalDateTime departTime, int FID) {
        StringBuilder result = new StringBuilder();
        Pattern pattern = Pattern.compile("[A-Z]");
        Matcher matcher = pattern.matcher(airLinesName);
        while (matcher.find()) {
            result.append(matcher.group());
        }
        result.delete(result.length() - 1, result.length());
        result.append(MyDateTime.toDayAndMonth(departTime)).append(String.format("%04d", FID));
        return result.toString();
    }

    private static double getBaseCost(String departure, String destination, LocalDateTime departTime) {
        int distance = Math.abs(airPortUnrealDistance.get(departure)
                - airPortUnrealDistance.get(destination));
        LocalDateTime start = LocalDateTime.of(departTime.getYear(), departTime.getMonth(), departTime.getDayOfMonth(), 8, 0);
        LocalDateTime end = LocalDateTime.of(departTime.getYear(), departTime.getMonth(), departTime.getDayOfMonth(), 20, 0);
        double priceMultiByTime = 1;
        if (departTime.isAfter(start) && departTime.isBefore(end)) {
            priceMultiByTime = dayFlightPriceMulti;
        }
        return (distance * distanceMulti + baseCost) * priceMultiByTime;
    }
}
