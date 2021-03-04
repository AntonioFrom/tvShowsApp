package com.example.tvshowsapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;

import com.example.tvshowsapp.R;
import com.example.tvshowsapp.adapters.AdapterTVShows;
import com.example.tvshowsapp.databinding.ActivitySearchBinding;
import com.example.tvshowsapp.listeners.ListenerTVShow;
import com.example.tvshowsapp.models.ModelTvShow;
import com.example.tvshowsapp.viewmodels.ViewModelSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity implements ListenerTVShow {

    private ActivitySearchBinding searchBinding;
    private ViewModelSearch viewModelSearch;
    private List<ModelTvShow> tvShows = new ArrayList<>();
    private AdapterTVShows adapterTVShows;
    private int currentPage = 1;
    private int totalAvailablePages = 1;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        doinitialization();
    }

    private void doinitialization() {
        searchBinding.imageBack.setOnClickListener(view -> onBackPressed());
        searchBinding.tvshowsRecyclerView.setHasFixedSize(true);
        viewModelSearch = new ViewModelProvider(this).get(ViewModelSearch.class);
        adapterTVShows = new AdapterTVShows(tvShows, this);
        searchBinding.tvshowsRecyclerView.setAdapter(adapterTVShows);
        searchBinding.inputsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (timer != null) {
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().trim().isEmpty()) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            new Handler(Looper.getMainLooper()).post(() -> {
                                currentPage = 1;
                                totalAvailablePages = 1;
                                searchTVShows(s.toString());
                            });
                        }
                    }, 800);
                } else {
                    tvShows.clear();
                    adapterTVShows.notifyDataSetChanged();
                }
            }
        });
        searchBinding.tvshowsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!searchBinding.tvshowsRecyclerView.canScrollVertically(1)) {
                    if (!searchBinding.inputsearch.getText().toString().isEmpty()) {
                        if (currentPage < totalAvailablePages) {
                            currentPage += 1;
                            searchTVShows(searchBinding.inputsearch.getText().toString());
                        }
                    }
                }
            }
        });
        searchBinding.inputsearch.requestFocus();
    }

    private void searchTVShows(String query) {
        toggleLoading();
        viewModelSearch.searchTVShow(query, currentPage).observe(this, responseTVShow -> {
            toggleLoading();
            if (responseTVShow != null) {
                totalAvailablePages = responseTVShow.getTotalPages();
                if (responseTVShow.getTvShows() != null) {
                    int oldCount = tvShows.size();
                    tvShows.addAll(responseTVShow.getTvShows());
                    adapterTVShows.notifyItemRangeInserted(oldCount, tvShows.size());
                }
            }
        });
    }

    private void toggleLoading() {
        if (currentPage == 1) {
            if (searchBinding.getIsLoading() != null && searchBinding.getIsLoading()) {
                searchBinding.setIsLoading(false);
            } else {
                searchBinding.setIsLoading(true);
            }
        } else {
            if (searchBinding.getIsLoadingMore() != null && searchBinding.getIsLoadingMore()) {
                searchBinding.setIsLoadingMore(false);
            } else {
                searchBinding.setIsLoadingMore(true);
            }
        }
    }

    @Override
    public void onTVShowClicked(ModelTvShow tvShow) {
        Intent intent = new Intent(getApplicationContext(), TVShowDetailsActivity.class);
        intent.putExtra("tvShow", tvShow);
        startActivity(intent);
    }
}