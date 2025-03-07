package com.example.fobidb.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/teacher")
public class TeacherController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public List<Teacher> getTeachers() {
        return teacherService.getTeachers();
    }

    @PostMapping
    public void registerTeacher(@RequestBody Teacher teacher) {
        teacherService.addNewTeacher(teacher);
    }

    @DeleteMapping(path = "/{teacherId}")
    public void deleteTeacher(@PathVariable("teacherId") Long teacherId){
        teacherService.deleteTeacher(teacherId);
    }

    /*
    @PutMapping(path = "{teacherId}")
    public void updateTeacher(
        @PathVariable("teacherId") Long teacherId,
        @RequestParam(required = false) String surname,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String name_short,
        @RequestParam(required = false) String email,
        @RequestParam(required = false) int training_time){
        teacherService.updateTeacher(teacherId, surname, name, name_short, email, training_time);
    }
     */
}
