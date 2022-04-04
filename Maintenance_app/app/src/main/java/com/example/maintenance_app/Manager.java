package com.example.maintenance_app;

public class Manager {

    public String username, education, password;

    public Manager(){}

    public Manager(String _username, String _education, String _password){
        this.username = _username;
        this.education = _education;
        this.password = _password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
