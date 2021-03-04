package com.example.tvshowsapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.tvshowsapp.database.DatabaseTVShows;
import com.example.tvshowsapp.models.ModelTvShow;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class ViewModelWatchlist extends AndroidViewModel {

    private DatabaseTVShows databaseTVShows;

    public ViewModelWatchlist(@NonNull Application application) {
        super(application);
        databaseTVShows = DatabaseTVShows.getDatabaseTVShows(application);
    }

    public Flowable<List<ModelTvShow>> loadWatchlist() {
        return databaseTVShows.tvShowDao().getWacthList();
    }

    public Completable removeTVShowFromWishlist(ModelTvShow tvShow) {
        return databaseTVShows.tvShowDao().removeFromWatchList(tvShow);
    }
}
