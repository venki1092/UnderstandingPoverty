package com.example.venki.up.models.events.coupons;

/**
 * Created by yash on 8/20/2016.
 */
public class GroupOnResult {
    String id;
    String uuid;
    String dealURL;
    String title;
    String mediumImageUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDealURL() {
        return dealURL;
    }

    public void setDealURL(String dealURL) {
        this.dealURL = dealURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMediumImageUrl() {
        return mediumImageUrl;
    }

    public void setMediumImageUrl(String mediumImageUrl) {
        this.mediumImageUrl = mediumImageUrl;
    }
}
