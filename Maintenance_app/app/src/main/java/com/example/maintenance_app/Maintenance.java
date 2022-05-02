package com.example.maintenance_app;

public class Maintenance {

    String device;
    String date;
    String time;
    String type;
    String repeat;

    public Maintenance(String _device, String _date, String _time, String _type, String _repeat){
        this.device = _device;
        this.date = _date;
        this.time = _time;
        this.type = _type;
        this.repeat = _repeat;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

}
