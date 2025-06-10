/*
 *   * Author: Chris M.
 *   * @Date: 28.05.2025
 *   *
 *   * @Description: Request-Klasse für Kurse.
 *   *
 *   * @Last Update: 28.05.25, 13:06
 *   * @Reason:
 *
 *
 */

package com.example.fobidb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CourseRequest {
    @JsonProperty(value = "courseId", access = JsonProperty.Access.WRITE_ONLY)
    private Long id; // Das Feld bleibt intern "id", kann aber als "courseId" in JSON empfangen werden
    private Long rating;
    private String comment;
}
