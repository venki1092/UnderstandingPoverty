package com.app.venki.up.model.coupons;

import java.util.List;

/**
 * Created by yash on 8/20/2016.
 */
public class GroupOnDeals {
    List<GroupOnResult> deals;

    public List<GroupOnResult> getResults() {
        return deals;
    }


    public void setResults(List<GroupOnResult> coupons) {
        this.deals = coupons;
    }
}
