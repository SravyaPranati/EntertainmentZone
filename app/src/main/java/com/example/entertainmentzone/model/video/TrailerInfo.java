package com.example.entertainmentzone.model.video;

import java.io.Serializable;

public class TrailerInfo implements Serializable {
    private String shId;
    private String shTitle;
    private String shVideoKey;
    public TrailerInfo()
    {

    }

    public TrailerInfo(String shId, String shTitle, String shVideoKey) {
        this.shId = shId;
        this.shTitle = shTitle;
        this.shVideoKey = shVideoKey;
    }

    public String getShId() {
        return shId;
    }

    public void setShId(String shId) {
        this.shId = shId;
    }

    public String getShTitle() {
        return shTitle;
    }

    public void setShTitle(String shTitle) {
        this.shTitle = shTitle;
    }

    public String getShVideoKey() {
        return shVideoKey;
    }

    public void setShVideoKey(String shVideoKey) {
        this.shVideoKey = shVideoKey;
    }
}
