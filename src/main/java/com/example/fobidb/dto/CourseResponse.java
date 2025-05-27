package com.example.fobidb.dto;

import com.example.fobidb.entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * * @Author: Chris M.
 * * @Date: 08.04.2025
 * * @Description: Klasse für Veranstaltungen.
 * <p>
 * * @Last Update: 22.05.2025
 * * @Last Update by: Chris M.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {
    private String title;
    private String description;
    private String startDate;
    private Long duration;
    private Teacher contact;
    private Long rating;
    private List<String> comments;

    private String errorMessage; // Optional: für Fehlerbehandlung oder Validierung
}
