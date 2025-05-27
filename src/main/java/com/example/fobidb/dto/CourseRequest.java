package com.example.fobidb.dto;

import lombok.Data;

@Data
public class CourseRequest {
    private Long Id;
    private Long rating;
    private String comment;
}
