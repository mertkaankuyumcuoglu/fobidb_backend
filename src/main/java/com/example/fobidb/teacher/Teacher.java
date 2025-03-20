package com.example.fobidb.teacher;

import jakarta.persistence.*;

@Entity
@Table
public class Teacher {
    @Id
    @SequenceGenerator(
        name = "teacher_sequence",
        sequenceName = "teacher_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "teacher_sequence"
    )
    public Long id;
    public String surname;
    public String name;
    public String nameshort;
    public String email;
    public Integer trainingtime;

    public Teacher() {
    }

//    public Teacher(Long id, String surname, String name, String nameshort, String email, Integer trainingtime) {
//        this.id = id;
//        this.surname = surname;
//        this.name = name;
//        this.nameshort = nameshort;
//        this.email = email;
//        this.trainingtime = trainingtime;
//    }

    public Teacher(String surname, String name, String nameshort, String email, Integer trainingtime) {
        this.surname = surname;
        this.name = name;
        this.nameshort = nameshort;
        this.email = email;
        this.trainingtime = trainingtime;
    }

    public Long getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getNameshort() {
        return nameshort;
    }

    public String getEmail() {
        return email;
    }

    public Integer getTrainingtime() {
        return trainingtime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNameshort(String nameshort) {
        this.nameshort = nameshort;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTrainingtime(Integer trainingtime) {
        this.trainingtime = trainingtime;
    }

    @Override
    public String toString() {
        return  "Teacher{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", nameshort='" + nameshort + '\'' +
                ", email='" + email + '\'' +
                ", trainingtime=" + trainingtime +
                '}';
    }
}
