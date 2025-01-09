package com.example.studybuddy.model;

import java.util.ArrayList;

public class Teacher extends User{
    protected String teacherName;
    protected String teacherPhone;
    protected String subject;
 protected ArrayList<String> subjects;
  protected double price;
  double rate;
  double sumRate;
  int numOfRate;


    // Constructor with all attributes (ensuring the super call is first)
    public Teacher(String id, String fname, String lname, String phone, String email, ArrayList<String> subjects, double price, double rate, double sumRate, int numOfRate) {
        super(id, fname, lname, phone, email);  // Calling the constructor of the superclass User
        this.subjects = subjects;
        this.price = price;
        this.rate = rate;
        this.sumRate = sumRate;
        this.numOfRate = numOfRate;
    }


    // Constructor for adding a new teacher
    public Teacher(String id, String teacherName, String teacherPhone, String subject) {
        super(id, teacherName, "", teacherPhone, "");  // Default or empty values for fname, lname, email, password
        this.teacherName = teacherName;
        this.teacherPhone = teacherPhone;
        this.subject = subject;
    }



    // Getters and Setters
    public ArrayList<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<String> subjects) {
        this.subjects = subjects;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getSumRate() {
        return sumRate;
    }

    public void setSumRate(double sumRate) {
        this.sumRate = sumRate;
    }

    public int getNumOfRate() {
        return numOfRate;
    }

    public void setNumOfRate(int numOfRate) {
        this.numOfRate = numOfRate;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "subjects=" + subjects +
                ", price=" + price +
                ", rate=" + rate +
                ", sumRate=" + sumRate +
                ", numOfRate=" + numOfRate +
                ", id='" + id + '\'' +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}



