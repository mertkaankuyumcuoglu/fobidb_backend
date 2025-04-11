package com.example.fobidb.relationship;

import com.example.fobidb.entity.Course;
import com.example.fobidb.entity.Department;
import com.example.fobidb.repository.CourseRepository;
import com.example.fobidb.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DepartmentCourseRelationshipTest {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void ShouldHaveOneToOneRelation() {
        Course course = new Course();
        course.setTitle("Course Title");
        course = courseRepository.save(course);

        Department department = new Department();
        department.setName("Department Name");
        department.setCourse(course);
        department = departmentRepository.save(department);

        Optional<Department> foundDepartment = departmentRepository.findById(department.getId());
        assertTrue(foundDepartment.isPresent());

        Department retrievedDepartment = foundDepartment.get();
        assertNotNull(retrievedDepartment.getCourse());
        assertEquals("Course Title", retrievedDepartment.getCourse().getTitle());
    }
}
