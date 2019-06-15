package com.example.entertainmentzone.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "shows")
public class ShowEntity {

    @NonNull
    @PrimaryKey
    String id;

    String title;
    String overview;
    String poster_path;
    String popularity;
    String rating;

    public ShowEntity(@NonNull String id, String title, String overview, String poster_path, String popularity, String rating) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.poster_path = poster_path;
        this.popularity = popularity;
        this.rating = rating;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getRating() {
        return rating;
    }
}
