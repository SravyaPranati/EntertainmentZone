package com.example.entertainmentzone.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import static android.arch.persistence.room.Room.databaseBuilder;

@Database(entities = {ShowEntity.class},version = 1,exportSchema = false)
public abstract class ShowDatabase extends RoomDatabase {
    public abstract ShowDao showDao();
    public static volatile ShowDatabase INSTANCE;
    static ShowDatabase getDatabase(final Context context)
    {
        if(INSTANCE==null){
            synchronized (ShowDatabase.class)
            {
                INSTANCE = databaseBuilder(context,ShowDatabase.class,"favShows.db")
                        .allowMainThreadQueries().build();
            }
        }
        return INSTANCE;
    }
}
