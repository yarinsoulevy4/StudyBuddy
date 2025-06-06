package com.example.studybuddy.model;

import java.io.Serializable;
import java.util.Objects;

public class Lesson implements Serializable {
    protected String id;
    protected String studentId; //the student
    protected String teacherId;// the teacher
    protected String subject;// subject of the lesson
    protected String date;
    protected String hour;

    protected String details;// little summary of the lesson- progress
    protected Boolean status;


    public Lesson() {
    }


    public Lesson(String id, String studentId, String teacherId, String subject, String hour, String details, String date, Boolean status) {
        this.id = id;
        this.studentId = studentId;
        this.teacherId = teacherId;
        this.subject = subject;
        this.hour = hour;
        this.details = details;
        this.date = date;
        this.status = status;
    }

    public Lesson(String id, User student, Teacher teacher, String date, String hour, String details, String subject, Boolean status) {
        this.id = id;
        this.studentId = studentId;
        this.teacherId = teacherId;
        this.subject = subject;
        this.date = date;
        this.hour = hour;
        this.details = details;
        this.status = status;
    }


    public String getTeacherId() {
        return teacherId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
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
                ", studentId='" + studentId + '\'' +
                ", teacherId='" + teacherId + '\'' +
                ", subject='" + subject + '\'' +
                ", date='" + date + '\'' +
                ", hour='" + hour + '\'' +
                ", details='" + details + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(id, lesson.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}