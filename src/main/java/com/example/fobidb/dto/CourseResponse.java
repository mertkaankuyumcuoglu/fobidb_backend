package com.example.fobidb.dto;/*
 *   * Author: Chris M.
 *   * @Date: 08.04.2025
 *   *
 *   * @Description: Response-Klasse für Kurse.
 *   *
 *   * @Last Update: 28.05.25, 13:06
 *   * @Reason:
 *
 *
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {
    private Long Id;
    private String title;
    private String description;
    private String startDate;
    private Long duration;
    private TeacherCourseResponse teacher;
    private Long rating;
    private List<String> comments;

    private String errorMessage; // Optional: für Fehlerbehandlung oder Validierung
}
