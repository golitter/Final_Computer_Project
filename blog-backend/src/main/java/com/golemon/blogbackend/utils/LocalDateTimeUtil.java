package com.golemon.blogbackend.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class LocalDateTimeUtil {
    private LocalDateTimeUtil() {
    }

    public static Map<String, LocalDate> getDateRange(String yearMonth) {
        // Parse string in "yyyy/MM/dd" format using DateTimeFormatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate date = LocalDate.parse(yearMonth + "/01", formatter);  // Default to the first day of each month

        Map<String, LocalDate> dateRange = new HashMap<>();

        // Set start date to the first day of the current month
        dateRange.put("start", date.withDayOfMonth(1));

        // Set end date to the first day of the next month
        LocalDate nextMonth = date.plusMonths(1).withDayOfMonth(1);
        dateRange.put("end", nextMonth);

        return dateRange;
    }

    public static void main(String[] args) {
        try {
            Map<String, LocalDate> range = getDateRange("2024/11");
            System.out.println("Start: " + range.get("start"));
            System.out.println("End: " + range.get("end"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
