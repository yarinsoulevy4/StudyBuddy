package com.example.studybuddy.model;

import java.io.Serializable;

public class Lesson implements Serializable


{


    public Lesson() {
    }

    protected String id;
    protected User student; //the student
    protected Teacher teacher ;// the teacher
    protected  String subject;// subject of the lesson
    protected  String date;
    protected  String hour;

    protected  String details;// little summary of the lesson- progress




    public Lesson(String id, User student, Teacher teacher, String date, String hour, String details, String subject) {
        this.id = id;
        this.student = student;
        this.teacher = teacher;
        this.subject = subject;
        this.date = date;
        this.hour = hour;

        this.details = details;
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


        return date.substring(8,10)+"/"+ date.substring(5,7)+"/"+ date.substring(0,4);
    }

    public void setDate(String date) {



        this.date=date;


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
                '}';
    }
}
