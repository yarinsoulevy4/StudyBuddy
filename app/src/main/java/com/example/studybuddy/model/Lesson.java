package com.example.studybuddy.model;

import android.widget.SeekBar;

public class Lesson
{
    protected User student; //the student
    protected Teacher teacher ;// the teacher
    protected  String subject;// subject of the lesson
    protected  String date;
    protected  String hour;
    protected SeekBar status;// the number of lesson they are in
    protected  String details;// little summary of the lesson- progress


    public Lesson(User student, Teacher teacher, String subject, String date, String hour, SeekBar status, String details) {
        this.student = student;
        this.teacher = teacher;
        this.subject = subject;
        this.date = date;
        this.hour = hour;
        this.status = status;
        this.details = details;
    }

    public Lesson(String key, String teachername, String studentname, String days, String hours, String subject, String details, String open, User theUser) {
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

    public SeekBar getStatus() {
        return status;
    }

    public void setStatus(SeekBar status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "student=" + student +
                ", teacher=" + teacher +
                ", subject='" + subject + '\'' +
                ", date='" + date + '\'' +
                ", hour='" + hour + '\'' +
                ", status='" + status + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
