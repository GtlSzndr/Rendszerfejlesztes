package com.example.maintenance_app;

import java.util.Locale;

public class Category {

    private String name;
    private String id;
    private String normt;
    private String periods;
    private String instructions;





    public Category() {}

    public Category(String name, String id, String normt, String periods,String instructions) {
        this.name = name;
        this.id = id;
        this.normt = normt;
        this.periods = periods;
        this.instructions = instructions;


    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getNormt() {
        return normt;
    }

    public void setNormt(String normt) {
        this.normt = normt;
    }
    public String getPeriods() {
        return periods;
    }

    public void setPeriods(String periods) {
        this.periods = periods;
    }
    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

}
