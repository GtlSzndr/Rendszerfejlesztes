package com.example.maintenance_app;

public class edToManager {
    public String manager, education;

    public edToManager(){}

    public edToManager(String _manager, String _education){
        this.manager = _manager;
        this.education = _education;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String username) {
        this.manager = username;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

}
