package com.example.tvshowsapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

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
        getMostPopularTVShows();
    }

    private void getMostPopularTVShows() {
        activityMainBinding.setIsLoading(true);
        viewModel.getMostPopularTVShows(0).observe(this, mostPopularTVShowsResponce -> {
                    activityMainBinding.setIsLoading(false);
                    if (mostPopularTVShowsResponce != null) {
                        if (mostPopularTVShowsResponce.getTvShows() != null) {
                            tvShows.addAll(mostPopularTVShowsResponce.getTvShows());
                            adaptersTVShows.notifyDataSetChanged();
                        }
                    }
                }
        );

    }
}