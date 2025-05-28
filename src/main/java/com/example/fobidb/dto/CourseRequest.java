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

import lombok.Data;

@Data
public class CourseRequest {
    private Long Id;
    private Long rating;
    private String comment;
}
