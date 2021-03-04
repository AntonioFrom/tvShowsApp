package com.example.tvshowsapp.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.tvshowsapp.database.DatabaseTVShows;
import com.example.tvshowsapp.models.ModelTvShow;
import com.example.tvshowsapp.repositories.RepositoryTVShowDetail;
import com.example.tvshowsapp.response.ResponseTVShowDetail;
import io.reactivex.Completable;
import io.reactivex.Flowable;

public class ViewModelTVShowDetail extends AndroidViewModel {

    private RepositoryTVShowDetail repositoryTVShowDetail;
    private DatabaseTVShows databaseTVShows;

    public ViewModelTVShowDetail (@NonNull Application application) {
        super(application);
        repositoryTVShowDetail = new RepositoryTVShowDetail();
        databaseTVShows = DatabaseTVShows.getDatabaseTVShows(application);
    }
    public LiveData<ResponseTVShowDetail> getTVShowDetails(String tvShowId){
        return repositoryTVShowDetail.getTVShowDetails(tvShowId);
    }

    public Completable addToWatchlist (ModelTvShow tvShow){
        return databaseTVShows.tvShowDao().addToWatchList(tvShow);
    }
    public Flowable<ModelTvShow> getTvShowFromWatchlist (String tvShowId){
        return databaseTVShows.tvShowDao().getTVShowFromWatchlist(tvShowId);
    }
    public Completable removeTvShowFromWatchlist (ModelTvShow tvShow){
        return databaseTVShows.tvShowDao().removeFromWatchList(tvShow);
    }
}
