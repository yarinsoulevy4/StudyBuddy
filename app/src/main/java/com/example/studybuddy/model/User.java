package com.example.studybuddy.model;

public class User {

    String id, fname,lname, phone, email, password;

    public User() {
    }
    // Constructor with 7 parameters
    public User(String uid, String firstName, String lastName, String phone, String email, String password, String city) {
        this.id = uid;
        this.fname = firstName;
        this.lname = lastName;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }




    public User(String id, String fname, String lname, String phone, String email) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
        this.email = email;
    }

    protected String getId() {
        return id;
    }

    protected void setId(String id) {
        this.id = id;
    }

    protected String getFname() {
        return fname;
    }

    protected void setFname(String fname) {
        this.fname = fname;
    }

    protected String getLname() {
        return lname;
    }

    protected void setLname(String lname) {
        this.lname = lname;
    }

    protected String getPhone() {
        return phone;
    }

    protected void setPhone(String phone) {
        this.phone = phone;
    }

    protected String getEmail() {
        return email;
    }

    protected void setEmail(String email) {
        this.email = email;
    }

    protected String getPassword() {
        return password;
    }

    protected void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +

                '}';
    }
}
