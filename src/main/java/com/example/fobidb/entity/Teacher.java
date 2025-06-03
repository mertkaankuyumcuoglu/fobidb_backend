package com.example.fobidb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * ! Wichtig:
 * ! Lombok Annotation @Getter / @Setter generiert Getter, Setter, toString, equals und hashCode
 * ! Diese Annotation ist wichtig, da sie die Boilerplate-Codes reduziert
 */

/**
 ** @Author: Chris M., Michel P.
 ** @Date: 07.04.2025
 ** @Description: Klasse für Lehrer.
 *
 ** @Last Update: 28.05.2025
 ** @Last Update by: Michel P.
 */

// Lehrer
@Entity
@Getter
@Setter
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String lastName;
    private String name;
    private String shortName;
    private String email;
    private Integer trainingTime;

    @Column(nullable = false)
    private String passwordHash;

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

    public Teacher() {

    }

    // Des testes wegen
    public Teacher(String name, String lastName, String shortName, String email, int trainingTime) {
        this.name = name;
        this.lastName = lastName;
        this.shortName = shortName;
        this.email = email;
        this.trainingTime = trainingTime;
    }
}
