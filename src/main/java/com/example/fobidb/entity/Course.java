package com.example.fobidb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * ! Wichtig:
 * ! Lombok Annotation @Data generiert Getter, Setter, toString, equals und hashCode
 * ! Diese Annotation ist wichtig, da sie die Boilerplate-Codes reduziert
 */

/**
 ** @Author: Chris M.
 ** @Date: 08.04.2025
 ** @Description: Klasse für Veranstaltungen.
 *
 ** @Last Update: 22.05.2025
 ** @Last Update by: Chris M.
 ** @Reason: Teacher als Kontaktperson hinzugefügt
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;

    @ElementCollection
    private List<Long> rating;

    private Long ratingAvg;

    private Long ratingCount;

    @ElementCollection
    List<String> comments;

    @ManyToOne
    @JoinColumn(name = "contact_id")
    private Teacher contact;

    @OneToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
