package com.example.fobidb.teacher;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> getTeachers() {
        //ist getTeachers() eine von Spring bereitgestellte Methode?
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

    public void deleteTeacher(Long teacherId) {
        boolean exists = teacherRepository.existsById(teacherId);
        if (!exists) {
            throw new IllegalStateException("Teacher with id " + teacherId + " does not exist");
        }
        teacherRepository.deleteById(teacherId);
    }

    @Transactional
    public void updateTeacher(Long teacherId,
                              String surname,
                              String name,
                              String nameshort,
                              String email,
                              Integer trainingtime) {
        Teacher teacher = teacherRepository.findById(teacherId)
            .orElseThrow(() -> new IllegalStateException(
                "Teacher with id " + teacherId + " does not exist"));

        //Check for surname
        if (surname != null &&
            !surname.isEmpty() &&
            !Objects.equals(teacher.getSurname(), surname)) {
            Optional<Teacher> teacherOptional = teacherRepository
                .findTeacherBySurname(surname);
            teacher.setSurname(surname);
        }

        //Check for name
        if (name != null &&
            !name.isEmpty() &&
            !Objects.equals(teacher.getName(), name)) {
            Optional<Teacher> teacherOptional = teacherRepository
                .findTeacherByName(name);
            teacher.setName(name);
        }

        //Check for nameshort
        if (nameshort != null &&
            !nameshort.isEmpty() &&
            !Objects.equals(teacher.getNameshort(), nameshort)) {
            Optional<Teacher> teacherOptional = teacherRepository
                .findTeacherByNameshort(nameshort);
            teacher.setNameshort(nameshort);
        }

        //Check for email
        if (email != null &&
            !email.isEmpty() &&
            !Objects.equals(teacher.getEmail(), email)) {
            Optional<Teacher> teacherOptional = teacherRepository
                .findTeacherByEmail(email);
            if (teacherOptional.isPresent()) {
                throw new IllegalStateException("Email exists already");
            }
            teacher.setEmail(email);
        }

        // Check for trainingtime
        if (trainingtime != null && trainingtime > 0 && !Objects.equals(teacher.getTrainingtime(), trainingtime)) {
            teacher.setTrainingtime(trainingtime);
        }
    }
}
