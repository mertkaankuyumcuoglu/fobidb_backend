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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseController.class)
public class CourseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @MockBean
    private CourseRepository courseRepository;

    Calendar start = Calendar.getInstance();
    {
        start.set(2025, Calendar.APRIL, 8, 10, 0, 0);
    }
    Date startDate = start.getTime();

    Calendar end = Calendar.getInstance();
    {
        end.set(2025, Calendar.APRIL, 9, 10, 0, 0);
    }

    Date endDate = end.getTime();

    final Long calculatedDuration = courseService.calculateCourseDuration(startDate, endDate);

    Course course = new Course(1L, "Some Course", "This is a test course", start.getTime(), end.getTime(), null, null);

    @Test
    public void shouldReturnAllCourses() throws Exception {
        when(courseService.getAllCourses())
                .thenReturn(List.of(course));

        mockMvc.perform(get("/course"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Some Course"))
                .andExpect(jsonPath("$[0].description").value("This is a test course"))
                .andExpect(jsonPath("$[0].startDate").value(startDate.toString()))
                .andExpect(jsonPath("$[0].duration").value(calculatedDuration))
                .andExpect(jsonPath("$[0].contact").doesNotExist());

    }
    
    
}
