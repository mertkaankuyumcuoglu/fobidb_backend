/*
 *   * Author: Chris M.
 *   * @Date: 28.05.2025
 *   *
 *   * @Description: Testklasse für den CourseController.
 *   *
 *   * @Last Update: 28.05.25, 11:37
 *   * @Reason: Fehler wurden gefixt, die das Rating falsch berechnet haben
 *
 *
 */

package com.example.fobidb.controller;

import com.example.fobidb.entity.Course;
import com.example.fobidb.entity.Department;
import com.example.fobidb.entity.Teacher;
import com.example.fobidb.repository.CourseRepository;
import com.example.fobidb.service.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseController.class)
public class CourseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @MockBean
    private CourseRepository courseRepository;

    Calendar calendar = Calendar.getInstance();

    List<String> comments = List.of("Great course!", "Very informative.", "Loved the practical examples.");
    List<Long> ratings = List.of(5L, 4L, 3L);


    {
        calendar.set(2023, Calendar.OCTOBER, 1);
    }

    Date start = calendar.getTime();

    {
        calendar.set(2023, Calendar.OCTOBER, 2);
    }

    Date end = calendar.getTime();

    @Test
    void getCourses() throws Exception {
        when(courseService.calculateCourseRating(1L, 4L)).thenReturn(4L);

        Course course = new Course(
                1L,
                "some course",
                "some desc",
                start,
                end,
                ratings,
                (long) ratings.size(),
                courseService.calculateCourseRating(1L, 4L),
                comments,
                null,
                null
        );

        when(courseService.getAllCourses())
                .thenReturn(List.of(course));

        mockMvc.perform(get("/course"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("some course"));
    }

    @Test
    void createCourse() throws Exception {
        when(courseService.calculateCourseRating(1L, 4L)).thenReturn(4L);

        Course course = Course
                .builder()
                .Id(1L)
                .title("Course Title")
                .description("Course Description")
                .startDate(start)
                .endDate(end)
                .rating(ratings)
                .ratingAvg(courseService.calculateCourseRating(1L, 4L))
                .ratingCount((long) ratings.size())
                .comments(comments)
                .contact(Teacher.builder().name("Max").build())
                .department(Department.builder().name("Test Department").build())
                .build();


                /*new Course(
                1L,
                "Course Title",
                "Course Description",
                start,
                end,
                ratings,
                (long) ratings.size(),
                courseService.calculateCourseRating(1L, 4L),
                comments,
                null,
                null
        );*/

        when(courseRepository.save(course))
                .thenReturn(course);

        mockMvc.perform(post("/course")
                        .contentType("application/json")
                        .content("{\"id\": 1, \"title\": \"Course Title\", \"description\": \"Course Description\", \"startDate\": \"2023-10-01T00:00:00Z\", \"endDate\": \"2023-10-02T00:00:00Z\", \"rating\": [5, 4, 3], \"ratingAvg\": 4, \"ratingCount\": 3, \"comments\": [\"Great course!\", \"Very informative.\", \"Loved the practical examples.\"], \"contact\": {\"name\": \"Max\"}, \"department\": {\"name\": \"Test Department\"}}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.title").value("Course Title"))
                .andExpect(jsonPath("$.description").value("Course Description"));

    }

    @Test
    void rateCourse() throws Exception {
        when(courseService.calculateCourseRating(1L, 4L)).thenReturn(4L);

        Course course = new Course(
                1L,
                "some course",
                "some desc",
                start,
                end,
                ratings,
                (long) ratings.size(),
                courseService.calculateCourseRating(1L, 4L),
                comments,
                null,
                null
        );

        when(courseRepository.findById(1L))
                .thenReturn(Optional.of(course));

        when(courseService.calculateCourseRating(1L, 4L))
                .thenReturn(4L);

        mockMvc.perform(post("/course/rating")
                        .contentType("application/json")
                        .content("{\"id\":1,\"rating\":4,\"comment\":\"Great course!\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.title").value("some course"))
                .andExpect(jsonPath("$.rating").value(4))
                .andExpect(jsonPath("$.comments.length()").value(4));
    }
}
