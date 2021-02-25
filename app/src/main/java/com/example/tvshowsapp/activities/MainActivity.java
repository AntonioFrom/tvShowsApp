package com.example.tvshowsapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvshowsapp.R;
import com.example.tvshowsapp.adapters.AdaptersTVShows;
import com.example.tvshowsapp.databinding.ActivityMainBinding;
import com.example.tvshowsapp.models.ModelTvShow;
import com.example.tvshowsapp.repositories.RepositoryMostPopularTVShow;
import com.example.tvshowsapp.response.ResponseTVShow;
import com.example.tvshowsapp.viewmodels.ViewModelMostPopularTVShow;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewModelMostPopularTVShow viewModel;
    private ActivityMainBinding activityMainBinding;
    private List<ModelTvShow> tvShows = new ArrayList<>();
    private AdaptersTVShows adaptersTVShows;
    private int currentPage = 1;
    private int totalAvaiblePages = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        doInitialization();
    }

    private void doInitialization() {
        activityMainBinding.tvshowsRecyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(ViewModelMostPopularTVShow.class);
        adaptersTVShows = new AdaptersTVShows(tvShows);
        activityMainBinding.tvshowsRecyclerView.setAdapter(adaptersTVShows);
        activityMainBinding.tvshowsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!activityMainBinding.tvshowsRecyclerView.canScrollVertically(1)){
                    if (currentPage <= totalAvaiblePages){
                        currentPage +=1;
                        getMostPopularTVShows();
                    }
                }
            }
        });
        getMostPopularTVShows();
    }

    private void getMostPopularTVShows() {
        toggleLoading();
        viewModel.getMostPopularTVShows(currentPage).observe(this, mostPopularTVShowsResponce -> {
                    toggleLoading();
                    if (mostPopularTVShowsResponce != null) {
                        totalAvaiblePages = mostPopularTVShowsResponce.getTotalPages();
                        if (mostPopularTVShowsResponce.getTvShows() != null) {
                            int oldCount = tvShows.size();
                            tvShows.addAll(mostPopularTVShowsResponce.getTvShows());
                            adaptersTVShows.notifyItemRangeInserted(oldCount,tvShows.size());
                        }
                    }
                }
        );

    }

    private void toggleLoading() {
        if (currentPage == 1) {
            if (activityMainBinding.getIsLoading() != null && activityMainBinding.getIsLoading()) {
                activityMainBinding.setIsLoading(false);
            } else {
                activityMainBinding.setIsLoading(true);
            }
        } else {
            if (activityMainBinding.getIsLoadingMore() != null && activityMainBinding.getIsLoadingMore()) {
                activityMainBinding.setIsLoadingMore(false);
            } else {
                activityMainBinding.setIsLoadingMore(true);
            }
        }
    }
}