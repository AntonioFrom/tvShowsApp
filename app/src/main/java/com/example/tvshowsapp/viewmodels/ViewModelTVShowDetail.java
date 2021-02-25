package com.example.tvshowsapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tvshowsapp.repositories.RepositoryTVShowDetail;
import com.example.tvshowsapp.response.ResponseTVShowDetail;

public class ViewModelTVShowDetail extends ViewModel {

    private RepositoryTVShowDetail repositoryTVShowDetail;

    public ViewModelTVShowDetail () {
        repositoryTVShowDetail = new RepositoryTVShowDetail();
    }
    public LiveData<ResponseTVShowDetail> getTVShowDetails(String tvShowId){
        return repositoryTVShowDetail.getTVShowDetails(tvShowId);
    }
}
