
package com.example.tvshowsapp.models;

import com.google.gson.annotations.SerializedName;

public class ModelEpisode {

    @SerializedName("episode")
    private String mEpisode;
    @SerializedName("season")
    private String mSeason;
    @SerializedName("name")
    private String mName;
    @SerializedName("air_date")
    private String mAirDate;

    public String getmEpisode() {
        return mEpisode;
    }

    public String getmSeason() {
        return mSeason;
    }

    public String getmName() {
        return mName;
    }

    public String getmAirDate() {
        return mAirDate;
    }
}
