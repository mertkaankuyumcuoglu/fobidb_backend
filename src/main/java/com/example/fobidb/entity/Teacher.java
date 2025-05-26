package com.example.fobidb.entity;

import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 *! Wichtig:
 *! Lombok Annotation @Getter / @Setter generiert Getter, Setter, toString, equals und hashCode
 *! Diese Annotation ist wichtig, da sie die Boilerplate-Codes reduziert
 */

/**
 ** @Author: Chris M.
 ** @Date: 07.04.2025
 ** @Description: Klasse für Lehrer.
 *
 ** @Last Update: 08.04.2025
 ** @Last Update by: Chris M.
 */

// Lehrer
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {

    // Des testes wegen
    public Teacher(String name, String lastName, String shortName, String email, int trainingTime) {
        this.name = name;
        this.lastName = lastName;
        this.shortName = shortName;
        this.email = email;
        this.trainingTime = trainingTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lastName;
    private String name;
    private String shortName;
    private String email;
    private Integer trainingTime;

    @Nullable
    private String imageUrl;

    @ManyToMany
    @JoinTable(
            name = "teacher_course",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> course;

    @ManyToMany
    @JoinTable(
            name = "teacher_department",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    private List<Department> department;
}
