package com.example.fobidb.service;

import com.example.fobidb.entity.Course;
import com.example.fobidb.repository.CourseRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 ** @Author: Chris M.
 ** @Date: 08.04.2025
 ** @Description: Klasse für Veranstaltungen.
 *
 ** @Last Update: 08.04.2025
 ** @Last Update by: Chris M.
 */

@RequiredArgsConstructor
@Service
public class CourseService {
    private final CourseRepository courseRepository;

    // Berechnet die Dauer der Events in Stunden
    public Long calculateCourseDuration(Date start, Date end){
        // Berechnung der Dauer in Stunden
        long durationInMillis = end.getTime() - start.getTime();
        return durationInMillis / (1000 * 60 * 60);
    }

    public void createNewPost(Course course){

    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

}
