package com.example.fobidb.service;

import com.example.fobidb.entity.Course;
import com.example.fobidb.entity.Department;
import com.example.fobidb.entity.Teacher;
import com.example.fobidb.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * * Author: Chris M.
 * * @Date: 09.04.2025
 * *
 * * @Description: Testklasse für den CourseService.
 * *
 * * @Last Update: 22.05.2025
 * * @Reason: Anpassung der Testklasse an die neuen Anforderungen.
 */

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    public CourseServiceTest() {

    }

    @Test
    void calculateCourseDuration() {
        // Klasse CourseService
        CourseService courseService = new CourseService(null);

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
        long actualDuration = courseService.calculateCourseDuration(startDate, endDate);

        // Überprüfung des Ergebnisses
        assertEquals(expectedDuration, actualDuration);
    }

    @Test
    void getAllCourses() {
        Calendar start = Calendar.getInstance();
        start.set(2025, Calendar.APRIL, 8, 10, 0, 0);
        Date startDate = start.getTime();

        Calendar end = Calendar.getInstance();
        end.set(2025, Calendar.APRIL, 9, 10, 0, 0);
        Date endDate = end.getTime();

        Department department = new Department(1L, "Test Department");
        Teacher teacher = new Teacher("Name", "Nachname", "nn", "email", 20);

        List<String> comments = List.of("Comment 1", "Comment 2");
        List<Long> ratings = List.of(4L, 5L);

        when(courseService.calculateCourseRating(1L, 4L)).thenReturn(4L);

        Course course = new Course(
                1L,
                "some course",
                "some desc",
                startDate,
                endDate,
                ratings,
                (long) ratings.size(),
                courseService.calculateCourseRating(1L, 4L),
                comments,
                teacher,
                department
        );


        when(courseRepository.findAll())
                .thenReturn(List.of(course));


        List<Course> courses = courseService.getAllCourses();
        assertEquals(1, courses.size());
        assertEquals("some course", courses.get(0).getTitle());
        assertEquals(startDate, courses.get(0).getStartDate());
        assertEquals(endDate, courses.get(0).getEndDate());
        assertEquals(1L, courses.get(0).getId());
        assertEquals("Test Department", courses.get(0).getDepartment().getName());
        assertEquals("Name", courses.get(0).getContact().getName());

    }

    @Test
    void calculateCourseRating(){


        courseService.calculateCourseRating(1L, 4L);
    }
}