package com.example.fobidb.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 *! Wichtig:
 *! Lombok Annotation @Data generiert Getter, Setter, toString, equals und hashCode
 *! Diese Annotation ist wichtig, da sie die Boilerplate-Codes reduziert
 */

/**
 *? Vielleicht sind die Beziehungen zwischen den Entitäten nicht korrekt
 *? Dies gilt zu überprüfen und zu testen
 */

/**
 ** @Author: Chris M.
 ** @Date: 08.04.2025
 ** @Description: Klasse für Fachbereiche.
 *
 ** @Last Update: 08.04.2025
 ** @Last Update by: Chris M.
 */

@Entity
@Data
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;

    @OneToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
