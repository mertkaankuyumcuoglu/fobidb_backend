package com.example.fobidb.controller;

import com.example.fobidb.dto.CourseRequest;
import com.example.fobidb.dto.CourseResponse;
import com.example.fobidb.entity.Course;
import com.example.fobidb.repository.CourseRepository;
import com.example.fobidb.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * * @Author: Chris M.
 * * @Date: 08.04.2025
 * * @Description: Klasse für Veranstaltungen.
 * <p>
 * * @Last Update: 27.05.2025
 * * @Last Update by: Chris M.
 * * @Reason: rating wurde hinzugefügt, um Bewertungen und Kommentare zu ermöglichen
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/course")
public class CourseController {
    private final CourseService courseService;
    private final CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAll() {
        List<Course> courses = courseService.getAllCourses();

        List<CourseResponse> courseResponses = courses.stream()
                .map(course -> new CourseResponse(
                        course.getTitle(),
                        course.getDescription(),
                        course.getStartDate().toString(),
                        courseService.calculateCourseDuration(
                                course.getStartDate(),
                                course.getEndDate()
                        ),
                        course.getContact(),
                        course.getRatingAvg(),
                        course.getComments() != null ? course.getComments() : new ArrayList<>(),
                        null

                ))
                .toList();

        return ResponseEntity.ok(courseResponses);
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        courseService.createNewCourse(course);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/rating")
    public ResponseEntity<CourseResponse> courseRating(@RequestBody CourseRequest courseRequest) {
        if (courseRequest.getRating() != null || courseRequest.getComment() != null) {
            Course course = courseRepository.findById(courseRequest.getId()).orElse(null);

            if (course != null) {
                if (courseRequest.getRating() != null) {
                    course.setRatingAvg(courseService.calculateCourseRating(course.getId(), courseRequest.getRating()));
                    courseRepository.save(course);
                }

                if (courseRequest.getComment() != null) {
                    List<String> comments = new ArrayList<>(course.getComments());
                    comments.add(courseRequest.getComment());
                    course.setComments(comments);
                    courseRepository.save(course);
                }

                // Erstelle CourseResponse mit aktualisierten Daten
                CourseResponse courseResponse = new CourseResponse(
                        course.getTitle(),
                        course.getDescription(),
                        course.getStartDate().toString(),
                        courseService.calculateCourseDuration(course.getStartDate(), course.getEndDate()),
                        course.getContact(),
                        course.getRatingAvg(),
                        course.getComments(),
                        null
                );

                return ResponseEntity.ok(courseResponse);
            }
        }

        return ResponseEntity.badRequest().build();
    }

}
