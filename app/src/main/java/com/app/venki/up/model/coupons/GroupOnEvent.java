package com.app.venki.up.model.coupons;



import java.util.List;

/**
 * Created by yash on 8/20/2016.
 */
public class GroupOnEvent {
    /*List<GroupOnDeals> deals;

    public List<GroupOnDeals> getDeals() {
        return deals;
    }

    public void setDeals(List<GroupOnDeals> deals) {
        this.deals = deals;
    }*/

    List<GroupOnResult> deals;

    public List<GroupOnResult> getResults() {
        return deals;
    }


    public void setResults(List<GroupOnResult> coupons) {
        this.deals = coupons;
    }

}
