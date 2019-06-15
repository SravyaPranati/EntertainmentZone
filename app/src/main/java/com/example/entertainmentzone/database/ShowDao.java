package com.example.entertainmentzone.database;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ShowDao {

    @Insert
    void  insert(ShowEntity showEntity);

    @Delete
    void delete(ShowEntity showEntity);

    @Query("select * from shows")
    LiveData<List<ShowEntity>> getAllFavShows();

    @Query("select * from shows where id = :id")
    ShowEntity checkMovie(String  id);

}
