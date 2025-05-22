package com.example.fobidb.controller;

import com.example.fobidb.dto.CourseResponse;
import com.example.fobidb.entity.Course;
import com.example.fobidb.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 ** @Author: Chris M.
 ** @Date: 08.04.2025
 ** @Description: Klasse für Veranstaltungen.
 *
 ** @Last Update: 22.05.2025
 ** @Last Update by: Chris M.
 ** @Reason: GET Methode hinzugefügt, um alle Kurse abzurufen
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/course")
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAll() {
        List<Course> courses = courseService.getAllCourses();

        List<CourseResponse> courseResponses = courses.stream()
                .map(course -> new CourseResponse(
                        course.getTitle(),
                        course.getDescription(),
                        course.getStartDate().toString(),
                        courseService.calculateCourseDuration(course.getStartDate(), course.getEndDate()),
                        course.getContact()))
                .toList();

        return ResponseEntity.ok(courseResponses);
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(Course course) {
        courseService.createNewCourse(course);
        return ResponseEntity.ok(course);
    }

}
