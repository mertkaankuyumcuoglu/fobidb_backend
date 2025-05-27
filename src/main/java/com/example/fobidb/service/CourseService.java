package com.example.fobidb.service;

import com.example.fobidb.entity.Course;
import com.example.fobidb.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 ** @Author: Chris M.
 ** @Date: 08.04.2025
 ** @Description: Klasse für Veranstaltungen.
 **
 ** @Last Update: 22.05.2025
 ** @Last Update by: Chris M.
 *
 * @Reason: Course erstellen, Alle kurse erhalten
 */

@RequiredArgsConstructor
@Service
public class CourseService {
    private final CourseRepository courseRepository;


    // Berechnet die Dauer der Events in Stunden
    public Long calculateCourseDuration(Date start, Date end) {
        // Berechnung der Dauer in Stunden
        long durationInMillis = end.getTime() - start.getTime();
        return durationInMillis / (1000 * 60 * 60);
    }

    public void createNewCourse(Course course) {
        courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Long CalculateCourseRating(Long Id, Long ratingToAdd){
        Course course = courseRepository.findById(Id).isPresent() ? courseRepository.findById(Id).get() : null;

        if(course == null) return null;

        Long currentRating = course.getRating();

        if(currentRating == null || currentRating < 0 || currentRating > 5 || currentRating == 0) return currentRating;

        course.setRatingCount(course.getRatingCount() + 1);

        // Berechnung der neuen Bewertung
        double newRating = ((currentRating * course.getRatingCount()) + ratingToAdd) / (course.getRatingCount() + 1);
        course.setRating(newRating);
        courseRepository.save(course);
        return newRating;
    }

}
