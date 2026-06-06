package com.phantakan.bookapi.utils;

import java.time.LocalDate;

public class DateUtils {
    // Buddhist year offset = 543
    private static final int BUDDHIST_YEAR_OFFSET = 543;

    // validate date range
    public static boolean isValidPublishedDate(LocalDate date) {
        LocalDate minDate = LocalDate.of(1001, 1, 1);
        LocalDate maxDate = LocalDate.now().plusYears(BUDDHIST_YEAR_OFFSET);
        return !date.isBefore(minDate) && !date.isAfter(maxDate);
    }

    // change date to Gregorian date
    public static LocalDate toGregorian(LocalDate date) {
        return date.minusYears(BUDDHIST_YEAR_OFFSET);
    }

    // change date to Buddhist date
    public static LocalDate toBuddhist(LocalDate date) {
        return date.plusYears(BUDDHIST_YEAR_OFFSET);
    }

    public static int getBuddhistYearOffset() {
        return BUDDHIST_YEAR_OFFSET;
    }
}
