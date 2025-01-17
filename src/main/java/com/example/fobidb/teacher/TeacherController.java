package com.example.fobidb.teacher;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/teacher")
public class TeacherController {

    @GetMapping
    public List<Teacher> getTeachers() { //ist getTeachers() eine von Spring bereitgestellte Methode?
        return List.of(
                new Teacher(
                        1,
                        "Schubert",
                        "Simon",
                        "sb",
                        "simon.schubert@aloberlin.de",
                        45
                )
        );
    }

}
