package com.example.fobidb.service;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CourseServiceTest {

    @Test
    void calculateCourseDuration() {
        // Klasse CourseService
        CourseService courseService = new CourseService();

        // Testdaten
        Calendar start = Calendar.getInstance();
        start.set(2025, Calendar.APRIL, 8, 10, 0, 0);
        Date startDate = start.getTime();

        Calendar end = Calendar.getInstance();
        end.set(2025, Calendar.APRIL, 9, 10, 0, 0);
        Date endDate = end.getTime();

        // Erwartete Dauer in Stunden
        long expectedDuration = 24; // 1 Tag = 24 Stunden

        // Berechnung der Dauer
        long actualDuration = courseService.CalculateCourseDuration(startDate, endDate);

        // Überprüfung des Ergebnisses
        assertEquals(expectedDuration, actualDuration);
    }
}