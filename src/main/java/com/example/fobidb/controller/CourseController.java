package com.example.fobidb.controller;

import com.example.fobidb.dto.CourseRequest;
import com.example.fobidb.dto.CourseResponse;
import com.example.fobidb.dto.TeacherCourseResponse;
import com.example.fobidb.entity.Course;
import com.example.fobidb.entity.Teacher;
import com.example.fobidb.repository.CourseRepository;
import com.example.fobidb.repository.TeacherRepository;
import com.example.fobidb.service.CourseService;
import com.example.fobidb.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * * @Author: Chris M.
 * * @Date: 08.04.2025
 * * @Description: Klasse für Veranstaltungen.
 * <p>
 * * @Last Update: 10.06.2025
 * * @Last Update by: System
 * * @Reason: Einmalige Bewertung pro Benutzer implementiert
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/course")
public class CourseController {
    private final CourseService courseService;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final JwtService jwtService;

    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAll() {
        List<Course> courses = courseService.getAllCourses();

        List<CourseResponse> courseResponses = courses.stream()
                .map(course -> {
                    // Konvertiere Teacher zu TeacherCourseResponse
                    Teacher teacher = course.getContact();
                    TeacherCourseResponse teacherResponse = null;
                    if (teacher != null) {
                        // Hole das erste Department aus der Liste oder null, wenn die Liste leer ist
                        String departmentName = null;
                        if (teacher.getDepartment() != null && !teacher.getDepartment().isEmpty()) {
                            departmentName = teacher.getDepartment().get(0).getName();
                        }

                        teacherResponse = new TeacherCourseResponse(
                                teacher.getId().toString(),
                                teacher.getFirstName(),
                                teacher.getLastName(),
                                teacher.getShortName(),
                                departmentName
                        );
                    }

                    return new CourseResponse(
                            course.getId(), // Hinzugefügt: ID des Kurses als erster Parameter
                            course.getTitle(),
                            course.getDescription(),
                            course.getStartDate().toString(),
                            courseService.calculateCourseDuration(
                                    course.getStartDate(),
                                    course.getEndDate()
                            ),
                            teacherResponse, // Konvertiertes TeacherCourseResponse-Objekt
                            course.getRatingAvg(),
                            course.getComments() != null ? course.getComments() : new ArrayList<>(),
                            null
                    );
                })
                .toList();

        return ResponseEntity.ok(courseResponses);
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        courseService.createNewCourse(course);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/rating")
    public ResponseEntity<?> courseRating(@RequestBody CourseRequest courseRequest, @RequestHeader("Authorization") String token) {
        // Token-Verarbeitung - erhält die ID des eingeloggten Lehrers
        Long teacherId = extractTeacherIdFromToken(token);

        if (teacherId == null) {
            return ResponseEntity.status(401).body("Nicht authentifiziert");
        }

        if (courseRequest.getId() == null) {
            return ResponseEntity.badRequest().body("Kurs-ID fehlt");
        }

        try {
            // Prüfen, ob eine Bewertung oder ein Kommentar vorhanden ist
            if (courseRequest.getRating() != null ||
                    (courseRequest.getComment() != null && !courseRequest.getComment().trim().isEmpty())) {

                // Kurs mit der neuen Bewertung bewerten
                Long newRating = courseService.rateCourse(
                        courseRequest.getId(),
                        teacherId,
                        courseRequest.getRating(),
                        courseRequest.getComment()
                );

                // Aktualisierte Kursinformationen abrufen
                Course course = courseRepository.findById(courseRequest.getId()).orElse(null);

                if (course != null) {
                    // Konvertiere Teacher zu TeacherCourseResponse
                    Teacher teacher = course.getContact();
                    TeacherCourseResponse teacherResponse = null;
                    if (teacher != null) {
                        // Hole das erste Department aus der Liste oder null, wenn die Liste leer ist
                        String departmentName = null;
                        if (teacher.getDepartment() != null && !teacher.getDepartment().isEmpty()) {
                            departmentName = teacher.getDepartment().get(0).getName();
                        }

                        teacherResponse = new TeacherCourseResponse(
                                teacher.getId().toString(),
                                teacher.getFirstName(),
                                teacher.getLastName(),
                                teacher.getShortName(),
                                departmentName
                        );
                    }

                    // Erstelle CourseResponse mit aktualisierten Daten
                    CourseResponse courseResponse = new CourseResponse(
                            course.getId(),
                            course.getTitle(),
                            course.getDescription(),
                            course.getStartDate().toString(),
                            courseService.calculateCourseDuration(course.getStartDate(), course.getEndDate()),
                            teacherResponse,
                            course.getRatingAvg(),
                            course.getComments(),
                            null
                    );

                    return ResponseEntity.ok(courseResponse);
                }
            }

            return ResponseEntity.badRequest().body("Keine Bewertung oder Kommentar angegeben");

        } catch (IllegalStateException e) {
            // Wenn der Lehrer den Kurs bereits bewertet hat
            return ResponseEntity.status(409).body(e.getMessage()); // 409 Conflict
        } catch (IllegalArgumentException e) {
            // Wenn der Kurs oder der Lehrer nicht gefunden werden konnte
            return ResponseEntity.status(404).body(e.getMessage()); // 404 Not Found
        } catch (Exception e) {
            // Andere unerwartete Fehler
            return ResponseEntity.status(500).body("Ein unerwarteter Fehler ist aufgetreten"); // 500 Internal Server Error
        }
    }

    /**
     * Extrahiert die Lehrer-ID aus dem JWT-Token
     *
     * @param authHeader Der Authorization-Header mit dem Bearer-Token
     * @return Die ID des Lehrers oder null, wenn das Token ungültig ist
     */
    private Long extractTeacherIdFromToken(String authHeader) {
        // Hier muss die tatsächliche Token-Validierung und Extraktion der Lehrer-ID implementiert werden
        // Beispiel (Pseudo-Code):
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String userEmail = jwtService.extractEmail(token);

            if (userEmail != null) {
                Optional<Teacher> teacherOpt = teacherRepository.findTeacherByEmail(userEmail);
                if (teacherOpt.isPresent()) {
                    return teacherOpt.get().getId();
                }
            }
        }
        return null;
    }

}
