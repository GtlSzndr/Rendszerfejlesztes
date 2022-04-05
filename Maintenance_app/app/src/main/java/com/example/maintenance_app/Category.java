package com.example.maintenance_app;

import java.util.Locale;

public class Category {

    private String category;
    private String name;
    private String id;

    public Category() {}

    public Category(String category, String name, String id) {
        this.category = category;
        this.name = name;
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
}
