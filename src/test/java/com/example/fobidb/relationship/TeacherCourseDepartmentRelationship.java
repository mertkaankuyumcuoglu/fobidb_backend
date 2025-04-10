package com.example.fobidb.relationship;

import com.example.fobidb.entity.Course;
import com.example.fobidb.entity.Department;
import com.example.fobidb.entity.Teacher;
import com.example.fobidb.repository.CourseRepository;
import com.example.fobidb.repository.DepartmentRepository;
import com.example.fobidb.repository.TeacherRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TeacherCourseDepartmentRelationship {
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    void ShouldHaveValidTeacherCourse() {
        Course course = new Course();
        course.setTitle("Course Title");

        List<Course> courses = new ArrayList<>();
        courses.add(course);
        course = courseRepository.save(course);

        Department department = new Department();
        department.setName("Department Name");
        department.setCourse(course);

        List<Department> departments = new LinkedList<>();
        departments.add(department);
        departmentRepository.save(department);


        Teacher teacher = new Teacher();
        teacher.setName("Max");
        teacher.setCourse(courses);
        teacher.setDepartment(departments);
        teacher = teacherRepository.save(teacher);

        Optional<Teacher> optionalTeacher = teacherRepository.findById(teacher.getId());
        assertTrue(optionalTeacher.isPresent());

        Teacher returnedTeacher = optionalTeacher.get();
        assertNotNull(returnedTeacher.getCourse());
        assertNotNull(returnedTeacher.getDepartment());
        assertEquals("Course Title", returnedTeacher.getCourse().get(0).getTitle());

        log.info("Teacher: " + returnedTeacher.getName());
        log.info("Course: " + returnedTeacher.getCourse().get(0).getTitle());
        log.info("Department: " + returnedTeacher.getDepartment().get(0).getName());
    }
}
