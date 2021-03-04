package com.example.tvshowsapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.tvshowsapp.models.ModelTvShow;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface TVShowDao {

    @Query("SELECT * FROM tvShows")
    Flowable<List<ModelTvShow>> getWacthList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addToWatchList(ModelTvShow tvShow);

    @Delete
    Completable removeFromWatchList(ModelTvShow tvShow);

    @Query("SELECT * FROM tvShows WHERE mId =:tvShowId")
    Flowable<ModelTvShow> getTVShowFromWatchlist (String tvShowId);
}
