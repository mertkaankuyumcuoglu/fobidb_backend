package com.example.fobidb.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 *! Wichtig:
 *! Lombok Annotation @Data generiert Getter, Setter, toString, equals und hashCode
 *! Diese Annotation ist wichtig, da sie die Boilerplate-Codes reduziert
 */

// Veranstaltung
@Entity
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String title;
    private Date startDate;
    private Date endDate;

    @OneToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
