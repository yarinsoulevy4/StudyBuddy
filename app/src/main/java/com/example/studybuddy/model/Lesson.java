package com.example.studybuddy.model;

import java.io.Serializable;

public class Lesson implements Serializable {
    protected String id;
    protected User student; //the student
    protected Teacher teacher;// the teacher
    protected String subject;// subject of the lesson
    protected String date;
    protected String hour;

    protected String details;// little summary of the lesson- progress
    protected Boolean status;


    public Lesson() {
    }


    public Lesson(String id, User student, Teacher teacher, String date, String hour, String details, String subject, Boolean status) {
        this.id = id;
        this.student = student;
        this.teacher = teacher;
        this.subject = subject;
        this.date = date;
        this.hour = hour;

        this.details = details;
        this.status = status;
    }


    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;

    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }


    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id='" + id + '\'' +
                ", student=" + student +
                ", teacher=" + teacher +
                ", subject='" + subject + '\'' +
                ", date='" + date + '\'' +
                ", hour='" + hour + '\'' +
                ", details='" + details + '\'' +
                ", status=" + status +
                '}';
    }
}