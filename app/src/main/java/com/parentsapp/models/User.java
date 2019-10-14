package com.parentsapp.models;

public class User {
    String uid, parent_name, student_name, email;

    public User(String uid, String parent_name, String student_name, String email) {
        this.uid = uid;
        this.parent_name = parent_name;
        this.student_name = student_name;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
