package com.app.venki.up.model;

import java.util.Date;

/**
 * Created by bala on 8/5/16.
 */
public class Post {
    private String id;
    private String email;
    private String post;
    private Date date;
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
