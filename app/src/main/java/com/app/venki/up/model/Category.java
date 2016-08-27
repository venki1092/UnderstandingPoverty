package com.app.venki.up.model;

/**
 * Created by yash on 8/21/2016.
 */
public class Category {

    private String id;
    private String name;


    public Category(String id, String name) {
        this.id = id;
        this.name = name;
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


    //to display object as a string in spinner
    public String toString() {
        return name;
    }

}