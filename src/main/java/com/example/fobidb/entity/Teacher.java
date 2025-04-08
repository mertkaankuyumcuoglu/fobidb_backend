package com.example.fobidb.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long Id;

    public String lastName;
    public String name;
    public String nameShort;
    public String email;
    public Integer trainingTime;
}
