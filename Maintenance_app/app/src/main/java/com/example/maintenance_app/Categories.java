package com.example.maintenance_app;

public class Categories {

    private String device_cat;
    private String education_cat;
    private String interval;
    private int std_time;
    private String task_steps;

    public Categories(){}

    public Categories(String _device_cat, String _education_cat) {
        this.device_cat = _device_cat;
        this.education_cat = _education_cat;

    }


    public String getDevice_cat() {
        return device_cat;
    }

    public void setDevice_cat(String device_cat) {
        this.device_cat = device_cat;
    }

    public String getEducation_cat() {
        return education_cat;
    }

    public void setEducation_cat(String education_cat) {
        this.education_cat = education_cat;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public int getStd_time() {
        return std_time;
    }

    public void setStd_time(int std_time) {
        this.std_time = std_time;
    }

    public String getTask_steps() {
        return task_steps;
    }

    public void setTask_steps(String task_steps) {
        this.task_steps = task_steps;
    }
}
