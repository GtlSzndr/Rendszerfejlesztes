package com.example.maintenance_app;

public class states {
    public String device, state;

    public states(){}

    public states(String device, String state){
        this.device = device;
        this.state = state;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
