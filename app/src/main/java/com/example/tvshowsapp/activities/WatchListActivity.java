package com.example.tvshowsapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.tvshowsapp.R;
import com.example.tvshowsapp.databinding.ActivityWatchListBinding;
import com.example.tvshowsapp.viewmodels.ViewModelWatchlist;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WatchListActivity extends AppCompatActivity {

    private ActivityWatchListBinding watchListBinding;
    private ViewModelWatchlist viewModelWatchlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        watchListBinding = DataBindingUtil.setContentView(this, R.layout.activity_watch_list);
        doInitialization();
    }

    private void doInitialization() {
        viewModelWatchlist = new ViewModelProvider(this).get(ViewModelWatchlist.class);
        watchListBinding.imageBack.setOnClickListener(v -> onBackPressed());
    }

    private void loadWatchlist() {
        watchListBinding.setIsLoading(true);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(viewModelWatchlist.loadWatchlist().subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(modelTvShows -> {
                    watchListBinding.setIsLoading(false);
                    Toast.makeText(getApplicationContext(), "Watchlist: " + modelTvShows.size(), Toast.LENGTH_SHORT).show();
                }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadWatchlist();
    }
}