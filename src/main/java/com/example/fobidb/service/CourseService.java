package com.example.fobidb.service;

import java.util.Date;

public class CourseService {

    // Berechnet die Dauer der Events in Stunden
    public Long CalculateCourseDuration(Date start, Date end){
        long millis = end.getTime() - start.getTime();
        return millis / (1000 * 60 * 60 * 24);
    }
}
