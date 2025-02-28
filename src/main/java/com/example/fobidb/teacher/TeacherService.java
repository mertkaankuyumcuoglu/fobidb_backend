package com.example.fobidb.teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> getTeachers() { //ist getTeachers() eine von Spring bereitgestellte Methode?
        return teacherRepository.findAll();
    }

    public void addNewTeacher(Teacher teacher) {
       Optional<Teacher> teacherByEmail = teacherRepository
           .findTeacherByEmail(teacher.getEmail());
       if (teacherByEmail.isPresent()) {
           throw new IllegalStateException("Email exists already");
       }
       teacherRepository.save(teacher);
    }
}
