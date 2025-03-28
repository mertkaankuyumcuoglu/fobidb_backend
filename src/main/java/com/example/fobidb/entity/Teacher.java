package com.example.fobidb.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Teacher {
    @Id
    public Long id;
    public String surname;
    public String name;
    public String nameshort;
    public String email;
    public Integer trainingtime;
}
