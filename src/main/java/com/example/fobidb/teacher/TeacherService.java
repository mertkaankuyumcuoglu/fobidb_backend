package com.example.fobidb.teacher;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    public List<Teacher> getTeachers() { //ist getTeachers() eine von Spring bereitgestellte Methode?
        return List.of(
            new Teacher(
                1L,
                "Schubert",
                "Simon",
                "sb",
                "simon.schubert@aloberlin.de",
                45
            )
        );
    }
}
