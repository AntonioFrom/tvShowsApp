package com.example.tvshowsapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.tvshowsapp.R;
import com.example.tvshowsapp.repositories.RepositoryMostPopularTVShow;
import com.example.tvshowsapp.response.ResponseTVShow;
import com.example.tvshowsapp.viewmodels.ViewModelMostPopularTVShow;

public class MainActivity extends AppCompatActivity {

    private ViewModelMostPopularTVShow viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(ViewModelMostPopularTVShow.class);
        getMostPopularTVShows();
    }

    private void getMostPopularTVShows() {
        viewModel.getMostPopularTVShows(0).observe(this, mostPopularTVShowsResponce ->
                Toast.makeText(getApplicationContext(), "Total Pages: "
                        + mostPopularTVShowsResponce.getTotalPages(), Toast.LENGTH_LONG).show()

        );

    }
}