package com.example.tvshowsapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tvshowsapp.repositories.RepositoryMostPopularTVShow;
import com.example.tvshowsapp.response.ResponseTVShow;

public class ViewModelMostPopularTVShow extends ViewModel {

    private RepositoryMostPopularTVShow repositoryMostPopularTVShow;

    public ViewModelMostPopularTVShow(){
        repositoryMostPopularTVShow = new RepositoryMostPopularTVShow();
    }
    public LiveData<ResponseTVShow> getMostPopularTVShows (int page){
        return repositoryMostPopularTVShow.getMostPopularTVShows(page);
    }
}
