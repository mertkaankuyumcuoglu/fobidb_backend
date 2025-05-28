/*
 *   * Author: Chris M.
 *   * @Date: 22.05.2025
 *   *
 *   * @Description: Klasse für Fachbereiche.
 *   *
 *   * @Last Update: 28.05.25, 13:05
 *   * @Reason:
 *
 *
 */

package com.example.fobidb.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

/**
 * ! Wichtig:
 * ! Lombok Annotation @Data generiert Getter, Setter, toString, equals und hashCode
 * ! Diese Annotation ist wichtig, da sie die Boilerplate-Codes reduziert
 */

/**
 * ? Vielleicht sind die Beziehungen zwischen den Entitäten nicht korrekt.
 * ? Dies gilt zu überprüfen und zu testen
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
}
