package com.example.fobidb.service;

import com.example.fobidb.entity.Course;
import com.example.fobidb.entity.CourseRating;
import com.example.fobidb.entity.Teacher;
import com.example.fobidb.repository.CourseRatingRepository;
import com.example.fobidb.repository.CourseRepository;
import com.example.fobidb.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * * @Author: Chris M.
 * * @Date: 08.04.2025
 * * @Description: Klasse für Veranstaltungen.
 * *
 * * @Last Update: 10.06.2025
 * * @Last Update by: System
 *
 * @Reason: Implementation der einmaligen Bewertung pro Benutzer
 */

@RequiredArgsConstructor
@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseRatingRepository courseRatingRepository;
    private final TeacherRepository teacherRepository;

    // Berechnet die Dauer der Events in Stunden
    public Long calculateCourseDuration(Date start, Date end) {
        // Berechnung der Dauer in Stunden
        long durationInMillis = end.getTime() - start.getTime();
        return durationInMillis / (1000 * 60 * 60);
    }

    public void createNewCourse(Course course) {
        courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    /**
     * Bewertet einen Kurs durch einen bestimmten Lehrer.
     * Jeder Lehrer kann einen Kurs nur einmal bewerten.
     *
     * @param courseId  ID des zu bewertenden Kurses
     * @param teacherId ID des Lehrers, der die Bewertung abgibt
     * @param rating    Die abgegebene Bewertung (1-5 Sterne)
     * @param comment   Optional: Ein Kommentar zur Bewertung
     * @return Die aktualisierte Durchschnittsbewertung des Kurses
     * @throws IllegalArgumentException wenn der Kurs oder Lehrer nicht gefunden wird
     * @throws IllegalStateException    wenn der Lehrer den Kurs bereits bewertet hat
     */
    @Transactional
    public Long rateCourse(Long courseId, Long teacherId, Long rating, String comment) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Kurs nicht gefunden: " + courseId));

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("Lehrer nicht gefunden: " + teacherId));

        // Prüfen, ob der Lehrer den Kurs bereits bewertet hat
        if (courseRatingRepository.existsByTeacherAndCourse(teacher, course)) {
            throw new IllegalStateException("Du hast diesen Kurs bereits bewertet.");
        }

        // Neue Bewertung erstellen
        CourseRating courseRating = new CourseRating();
        courseRating.setCourse(course);
        courseRating.setTeacher(teacher);

        // Rating setzen, falls vorhanden (kann null sein, wenn nur kommentiert wird)
        if (rating != null) {
            // Validiere die Bewertung (zwischen 1 und 5)
            if (rating < 1 || rating > 5) {
                rating = null; // Ungültige Bewertungen werden ignoriert
            }
            courseRating.setRating(rating);
        }

        // Kommentar setzen, falls vorhanden und nicht leer
        if (comment != null && !comment.trim().isEmpty()) {
            courseRating.setComment(comment.trim());

            // Füge auch direkt zur Kommentarliste des Kurses hinzu (Legacy-Unterstützung)
            List<String> comments = course.getComments();
            if (comments == null) {
                comments = new java.util.ArrayList<>();
            } else {
                // Eine neue Kopie der Liste erstellen, um die bestehende nicht zu verändern
                comments = new java.util.ArrayList<>(comments);
            }
            comments.add(comment.trim());
            course.setComments(comments);
        }

        // Bewertung speichern
        courseRatingRepository.save(courseRating);

        // Aktualisiere den Durchschnitt nur, wenn eine gültige Bewertung vorhanden ist
        if (rating != null && rating >= 1 && rating <= 5) {
            return updateCourseAverageRating(course);
        } else {
            // Ansonsten nur die aktualisierte Kommentarliste speichern
            courseRepository.save(course);
            return course.getRatingAvg(); // Aktuelle Bewertung zurückgeben
        }
    }

    /**
     * Aktualisiert die Bewertung eines Kurses durch einen bestimmten Lehrer.
     *
     * @param courseId   ID des Kurses
     * @param teacherId  ID des Lehrers
     * @param newRating  Die neue Bewertung
     * @param newComment Optional: Ein neuer Kommentar
     * @return Die aktualisierte Durchschnittsbewertung des Kurses
     */
    @Transactional
    public Long updateCourseRating(Long courseId, Long teacherId, Long newRating, String newComment) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Kurs nicht gefunden: " + courseId));

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("Lehrer nicht gefunden: " + teacherId));

        // Bestehende Bewertung finden
        CourseRating courseRating = courseRatingRepository.findByTeacherAndCourse(teacher, course)
                .orElseThrow(() -> new IllegalStateException("Keine vorherige Bewertung gefunden"));

        // Bewertung aktualisieren
        courseRating.setRating(newRating);
        if (newComment != null && !newComment.trim().isEmpty()) {
            courseRating.setComment(newComment);
        }

        // Bewertung speichern
        courseRatingRepository.save(courseRating);

        // Durchschnittsbewertung neu berechnen
        return updateCourseAverageRating(course);
    }

    /**
     * Berechnet die Durchschnittsbewertung eines Kurses neu.
     *
     * @param course Der Kurs, dessen Bewertung aktualisiert werden soll
     * @return Die neue Durchschnittsbewertung
     */
    private Long updateCourseAverageRating(Course course) {
        // Alle Bewertungen für diesen Kurs abrufen
        List<CourseRating> ratings = course.getRatings();

        if (ratings == null || ratings.isEmpty()) {
            course.setRatingAvg(0L);
            course.setRatingCount(0L);
            courseRepository.save(course);
            return 0L;
        }

        // Summe aller Bewertungen berechnen
        long sum = 0;
        for (CourseRating rating : ratings) {
            sum += rating.getRating();
        }

        // Durchschnitt berechnen und aktualisieren
        long average = sum / ratings.size();
        course.setRatingAvg(average);
        course.setRatingCount((long) ratings.size());

        // Kommentare aktualisieren
        List<String> comments = course.getComments();
        if (comments == null) {
            comments = new java.util.ArrayList<>();
        }

        // Neue Liste mit allen Kommentaren aus den Bewertungen erstellen
        List<String> updatedComments = new java.util.ArrayList<>();
        for (CourseRating rating : ratings) {
            if (rating.getComment() != null && !rating.getComment().trim().isEmpty()) {
                updatedComments.add(rating.getComment());
            }
        }

        course.setComments(updatedComments);
        courseRepository.save(course);

        return average;
    }

    // Die alte calculateCourseRating-Methode bleibt zur Kompatibilität vorerst bestehen
    @Deprecated
    public Long calculateCourseRating(Long Id, Long ratingToAdd) {
        Course course = courseRepository.findById(Id).orElse(null);

        if (course == null) return null;

        // Initialisiere die Bewertungswerte, falls sie noch nicht gesetzt wurden
        if (course.getRatingAvg() == null) {
            course.setRatingAvg(0L);
        }

        if (course.getRatingCount() == null) {
            course.setRatingCount(0L);
        }

        if (course.getRating() == null) {
            course.setRating(new java.util.ArrayList<>());
        }

        // Füge die neue Bewertung zur Liste hinzu
        List<Long> ratings = course.getRating();
        ratings.add(ratingToAdd);
        course.setRating(ratings);

        // Erhöhe den Bewertungszähler um 1
        course.setRatingCount(course.getRatingCount() + 1);

        // Berechne die neue Durchschnittsbewertung
        Long ratingSum = 0L;
        for (Long rating : ratings) {
            ratingSum += rating;
        }

        Long newRating = ratingSum / course.getRatingCount();
        course.setRatingAvg(newRating);

        // Speichere das aktualisierte Kursobjekt
        courseRepository.save(course);

        return newRating;
    }
}
