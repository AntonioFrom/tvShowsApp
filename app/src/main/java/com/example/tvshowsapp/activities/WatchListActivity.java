package com.example.tvshowsapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.tvshowsapp.R;
import com.example.tvshowsapp.adapters.AdapterWatchlist;
import com.example.tvshowsapp.databinding.ActivityWatchListBinding;
import com.example.tvshowsapp.listeners.ListenerWatchList;
import com.example.tvshowsapp.models.ModelTvShow;
import com.example.tvshowsapp.utilities.TempDataHolder;
import com.example.tvshowsapp.viewmodels.ViewModelWatchlist;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WatchListActivity extends AppCompatActivity implements ListenerWatchList {

    private ActivityWatchListBinding watchListBinding;
    private ViewModelWatchlist viewModelWatchlist;
    private AdapterWatchlist adapterWatchlist;
    private List<ModelTvShow> watchlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        watchListBinding = DataBindingUtil.setContentView(this, R.layout.activity_watch_list);
        doInitialization();
    }

    private void doInitialization() {
        viewModelWatchlist = new ViewModelProvider(this).get(ViewModelWatchlist.class);
        watchListBinding.imageBack.setOnClickListener(v -> onBackPressed());
        watchlist = new ArrayList<>();
        loadWatchlist();
    }

    private void loadWatchlist() {
        watchListBinding.setIsLoading(true);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(viewModelWatchlist.loadWatchlist().subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(modelTvShows -> {
                    watchListBinding.setIsLoading(false);
                    if (watchlist.size() > 0) {
                        watchlist.clear();
                    }
                    watchlist.addAll(modelTvShows);
                    adapterWatchlist = new AdapterWatchlist(watchlist, this);
                    watchListBinding.watchlistRecyclerView.setAdapter(adapterWatchlist);
                    watchListBinding.watchlistRecyclerView.setVisibility(View.VISIBLE);
                    compositeDisposable.dispose();
                }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (TempDataHolder.IS_WATCHLIST_UPDATED) {
            loadWatchlist();
            TempDataHolder.IS_WATCHLIST_UPDATED = false;
        }
    }

    @Override
    public void onTVShowClicked(ModelTvShow tvShow) {
        Intent intent = new Intent(getApplicationContext(), TVShowDetailsActivity.class);
        intent.putExtra("tvShow", tvShow);
        startActivity(intent);
    }

    @Override
    public void removeTVShowFromWatchlist(ModelTvShow tvShow, int position) {
        CompositeDisposable compositeDisposableForDelete = new CompositeDisposable();
        compositeDisposableForDelete.add(viewModelWatchlist.removeTVShowFromWishlist(tvShow)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    watchlist.remove(position);
                    adapterWatchlist.notifyItemRemoved(position);
                    adapterWatchlist.notifyItemRangeChanged(position, adapterWatchlist.getItemCount());
                    compositeDisposableForDelete.dispose();
                }));
    }
}