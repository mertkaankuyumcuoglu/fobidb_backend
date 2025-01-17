package com.example.fobidb.teacher;

public class Teacher {
    public int id;
    public String surname;
    public String name;
    public String name_short;
    public String email;
    public int training_time;

    public Teacher() {
    }

    public Teacher(int id, String surname, String name, String name_short, String email, int training_time) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.name_short = name_short;
        this.email = email;
        this.training_time = training_time;
    }

    public Teacher(String surname, String name, String name_short, String email, int training_time) {
        this.surname = surname;
        this.name = name;
        this.name_short = name_short;
        this.email = email;
        this.training_time = training_time;
    }

    public int getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getName_short() {
        return name_short;
    }

    public String getEmail() {
        return email;
    }

    public int getTraining_time() {
        return training_time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setName_short(String name_short) {
        this.name_short = name_short;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTraining_time(int training_time) {
        this.training_time = training_time;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", name_short='" + name_short + '\'' +
                ", email='" + email + '\'' +
                ", training_time=" + training_time +
                '}';
    }
}
