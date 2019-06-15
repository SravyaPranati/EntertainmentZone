package com.example.entertainmentzone.model.review;

import java.io.Serializable;

public class ReviewInfo implements Serializable {

    private String author;
    private String content;

    public ReviewInfo()
    {

    }

    public ReviewInfo(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

