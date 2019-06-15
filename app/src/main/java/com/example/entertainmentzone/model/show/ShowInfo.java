package com.example.entertainmentzone.model.show;

import java.io.Serializable;

public class ShowInfo implements Serializable {
    String shId;
    String shName;
    String shRating;
    String shOverview;
    String shPopularity;
    String shPoster_path;
    String shBackdrop_path;

    public ShowInfo()
    {

    }

    public ShowInfo(String shId, String shName, String shRating, String shOverview, String shPopularity, String shPoster_path, String shBackdrop_path) {
        this.shId = shId;
        this.shName = shName;
        this.shRating = shRating;
        this.shOverview = shOverview;
        this.shPopularity = shPopularity;
        this.shPoster_path = shPoster_path;
        this.shBackdrop_path = shBackdrop_path;
    }

    public String getShId() {
        return shId;
    }

    public void setShId(String shId) {
        this.shId = shId;
    }

    public String getShName() {
        return shName;
    }

    public void setShName(String shName) {
        this.shName = shName;
    }

    public String getShRating() {
        return shRating;
    }

    public void setShRating(String shRating) {
        this.shRating = shRating;
    }

    public String getShOverview() {
        return shOverview;
    }

    public void setShOverview(String shOverview) {
        this.shOverview = shOverview;
    }

    public String getShPopularity() {
        return shPopularity;
    }

    public void setShPopularity(String shPopularity) {
        this.shPopularity = shPopularity;
    }

    public String getShPoster_path() {
        return shPoster_path;
    }

    public void setShPoster_path(String shPoster_path) {
        this.shPoster_path = shPoster_path;
    }

    public String getShBackdrop_path() {
        return shBackdrop_path;
    }

    public void setShBackdrop_path(String shBackdrop_path) {
        this.shBackdrop_path = shBackdrop_path;
    }
}
