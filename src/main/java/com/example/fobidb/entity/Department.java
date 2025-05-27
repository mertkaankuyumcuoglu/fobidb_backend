package com.example.fobidb.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ! Wichtig:
 * ! Lombok Annotation @Data generiert Getter, Setter, toString, equals und hashCode
 * ! Diese Annotation ist wichtig, da sie die Boilerplate-Codes reduziert
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
 ** @Last Update: 22.05.2025
 ** @Last Update by: Chris M.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
}
