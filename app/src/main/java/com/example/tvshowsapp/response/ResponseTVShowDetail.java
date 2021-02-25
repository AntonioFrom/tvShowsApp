package com.example.tvshowsapp.response;

import com.example.tvshowsapp.models.ModelTvShowDetail;
import com.google.gson.annotations.SerializedName;

public class ResponseTVShowDetail {

    @SerializedName("tvShow")
    private ModelTvShowDetail modelTvShowDetail;

    public ModelTvShowDetail getModelTvShowDetail() {
        return modelTvShowDetail;
    }
}
