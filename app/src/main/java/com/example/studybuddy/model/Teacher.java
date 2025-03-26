package com.example.studybuddy.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Teacher extends User implements Serializable {

    protected String subject;

  protected double price;
 protected double rate;
 protected double sumRate;
 protected int numOfRate;


    public Teacher() {
    }

    public Teacher(String subject, double price, double rate, double sumRate, int numOfRate) {
        this.subject = subject;
        this.price = price;
        this.rate = rate;
        this.sumRate = sumRate;
        this.numOfRate = numOfRate;
    }

    public Teacher(String id, String fname, String lname, String phone, String email, String password, String subject, double price, double rate, double sumRate, int numOfRate) {
        super(id, fname, lname, phone, email, password);
        this.subject = subject;
        this.price = price;
        this.rate = rate;
        this.sumRate = sumRate;
        this.numOfRate = numOfRate;
    }


    public Teacher(User user, String subject, double price, double rate, double sumRate, int numOfRate) {
        super(user);
        this.subject = subject;
        this.price = price;
        this.rate = rate;
        this.sumRate = sumRate;
        this.numOfRate = numOfRate;
    }

    public Teacher(Teacher teacher) {
        super(teacher);
        this.subject = teacher.getSubject();
        this.price = teacher.price;


    }

    public Teacher(String id, String fname, String lname, String phone, String email, String subject, double price, double rate, double sumRate, int numOfRate) {
        super(id, fname, lname, phone, email);
        this.subject = subject;
        this.price = price;
        this.rate = rate;
        this.sumRate = sumRate;
        this.numOfRate = numOfRate;
    }

// Constructor with all attributes (ensuring the super call is first)

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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }



    @Override
    public String toString() {
        return "Teacher{" +
                "subject='" + subject + '\'' +
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



