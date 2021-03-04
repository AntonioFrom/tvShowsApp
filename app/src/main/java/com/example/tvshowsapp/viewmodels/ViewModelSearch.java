package com.example.tvshowsapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tvshowsapp.repositories.RepositorySearchTvShow;
import com.example.tvshowsapp.response.ResponseTVShow;

public class ViewModelSearch extends ViewModel {

    private RepositorySearchTvShow repositorySearchTvShow;

    public ViewModelSearch(){
        repositorySearchTvShow = new RepositorySearchTvShow();
    }
    public LiveData<ResponseTVShow> searchTVShow (String query, int page){
        return repositorySearchTvShow.searchTVShow(query,page);
    }
    
}
