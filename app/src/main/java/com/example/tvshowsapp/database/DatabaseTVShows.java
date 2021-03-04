package com.example.tvshowsapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tvshowsapp.dao.TVShowDao;
import com.example.tvshowsapp.models.ModelTvShow;

@Database(entities = ModelTvShow.class, version = 1,exportSchema = false)
public abstract class DatabaseTVShows extends RoomDatabase {

    private static DatabaseTVShows databaseTVShows;

    public static synchronized DatabaseTVShows getDatabaseTVShows (Context context){
        if (databaseTVShows == null){
            databaseTVShows = Room.databaseBuilder(context,
                    DatabaseTVShows.class,"tv_show_db").build();
        }
        return databaseTVShows;
    }
    public abstract TVShowDao tvShowDao();
}
