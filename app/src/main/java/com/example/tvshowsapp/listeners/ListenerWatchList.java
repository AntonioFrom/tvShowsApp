package com.example.tvshowsapp.listeners;

import com.example.tvshowsapp.models.ModelTvShow;

public interface ListenerWatchList {

    void onTVShowClicked(ModelTvShow tvShow);

    void removeTVShowFromWatchlist(ModelTvShow tvShow,int position);
}
