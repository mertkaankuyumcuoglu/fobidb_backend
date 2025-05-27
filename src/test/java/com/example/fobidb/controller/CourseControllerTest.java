package com.example.fobidb.controller;

import com.example.fobidb.entity.Course;
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
    Calendar calendar = Calendar.getInstance();
    {
        calendar.set(2023, Calendar.OCTOBER, 1);
    }
    Date start = calendar.getTime();

    {
        calendar.set(2023, Calendar.OCTOBER, 2);
    }
    Date end = calendar.getTime();

    List<String> comments = List.of("Great course!", "Very informative.", "Loved the practical examples.");

    Course testCourse = new Course(
            1L,
            "some course",
            "some desc",
            start,
            end,
            3L,
            1L,
            comments,
            null,
            null
    );

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @MockBean
    private CourseRepository courseRepository;

    @Test
    void getCourses() throws Exception {
        when(courseService.getAllCourses())
                .thenReturn(List.of(testCourse));

        mockMvc.perform(get("/course"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("some course"));
    }

    @Test
    void createCourse() throws Exception {
        when(courseRepository.save(testCourse))
                .thenReturn(testCourse);

        mockMvc.perform(post("/course")
                .contentType("application/json")
                .content("{\"title\":\"some course\",\"description\":\"some desc\",\"startDate\":\"2023-10-01T00:00:00Z\",\"endDate\":\"2023-10-02T00:00:00Z\",\"rating\":3,\"ratingCount\":1,\"comments\":[\"Great course!\",\"Very informative.\",\"Loved the practical examples.\"]}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.title").value("some course"))
                .andExpect(jsonPath("$.description").value("some desc"));

    }

    @Test
    void rateCourse() throws Exception {
        when(courseRepository.findById(1L))
                .thenReturn(Optional.ofNullable(testCourse));

        when(courseService.CalculateCourseRating(1L, 4L))
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
