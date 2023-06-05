package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyDateTime {
    public static String getToday() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return now.format(formatter);
    }

    public static String toHourAndMinute(LocalDateTime time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(formatter);
    }
    public static String toDayAndMonth(LocalDateTime time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMM");
        return time.format(formatter);
    }
    public static String toDayMonthYear(LocalDateTime time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return time.format(formatter);
    }
    public static String toDayMMMMYear(LocalDateTime time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return time.format(formatter);
    }
}
