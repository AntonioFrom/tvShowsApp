package com.example.tvshowsapp.response;

import com.example.tvshowsapp.models.ModelTvShow;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseTVShow {

    @SerializedName("page")
    private int page;

    @SerializedName("pages")
    private int totalPages;

    @SerializedName("tv_shows")
    private List<ModelTvShow> tvShows;

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<ModelTvShow> getTvShows() {
        return tvShows;
    }
}
