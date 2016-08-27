package com.app.venki.up.model.job;

import java.util.List;

/**
 * Created by samsiu on 8/17/16.
 */
public class Indeed {

    int version;
    String query;
    String location;
    boolean dupefilter;
    boolean highlight;
    int radius;
    int start;
    int end;
    int totalResults;
    int pageNumber;
    List<IndeedResults> results;

    public int getVersion() {
        return version;
    }

    public String getQuery() {
        return query;
    }

    public String getLocation() {
        return location;
    }

    public boolean isDupefilter() {
        return dupefilter;
    }

    public boolean isHighlight() {
        return highlight;
    }

    public int getRadius() {
        return radius;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public List<IndeedResults> getResults() {
        return results;
    }
}

