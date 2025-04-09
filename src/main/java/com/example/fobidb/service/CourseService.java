package com.example.fobidb.service;

import java.util.Date;

/**
 ** @Author: Chris M.
 ** @Date: 08.04.2025
 ** @Description: Klasse für Veranstaltungen.
 *
 ** @Last Update: 08.04.2025
 ** @Last Update by: Chris M.
 */

public class CourseService {

    // Berechnet die Dauer der Events in Stunden
    public Long CalculateCourseDuration(Date start, Date end){
        // Berechnung der Dauer in Stunden
        long durationInMillis = end.getTime() - start.getTime();
        return durationInMillis / (1000 * 60 * 60);
    }
}
