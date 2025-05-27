package com.example.fobidb.dto;

import com.example.fobidb.entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 ** @Author: Chris M.
 ** @Date: 08.04.2025
 ** @Description: Klasse für Veranstaltungen.
 *
 ** @Last Update: 22.05.2025
 ** @Last Update by: Chris M.
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
}
