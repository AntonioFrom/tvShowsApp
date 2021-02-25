package com.example.tvshowsapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ModelTvShowDetail {


    @SerializedName("url")
    private String mUrl;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("runtime")
    private Long mRuntime;

    @SerializedName("image_path")
    private String mImagePath;

    @SerializedName("rating")
    private String mRating;

    @SerializedName("genres")
    private String[] mGenres;

    @SerializedName("pictures")
    private String[] mPictures;

    @SerializedName("episodes")
    private List<ModelEpisode> mEpisodes;

    public String getmUrl() {
        return mUrl;
    }

    public String getmDescription() {
        return mDescription;
    }

    public Long getmRuntime() {
        return mRuntime;
    }

    public String getmImagePath() {
        return mImagePath;
    }

    public String getmRating() {
        return mRating;
    }

    public String[] getmGenres() {
        return mGenres;
    }

    public String[] getmPictures() {
        return mPictures;
    }

    public List<ModelEpisode> getmEpisodes() {
        return mEpisodes;
    }
}

