package com.example.maintenance_app;

public class edToDevCat {
    public String deviceCategory, education;

    public edToDevCat(){}

    public edToDevCat(String deviceCategory, String _education){
        this.deviceCategory = deviceCategory;
        this.education = _education;
    }

    public String getdeviceCategory() {
        return deviceCategory;
    }

    public void setDeviceCategory(String username) {
        this.deviceCategory = username;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

}
