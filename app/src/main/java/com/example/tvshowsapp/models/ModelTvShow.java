
package com.example.tvshowsapp.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity (tableName = "tvShows")
public class ModelTvShow implements Serializable {

    @PrimaryKey
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("start_date")
    private String mStartDate;
    @SerializedName("country")
    private String mCountry;
    @SerializedName("network")
    private String mNetwork;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("image_thumbnail_path")
    private String mImageThumbnailPath;

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getImageThumbnailPath() {
        return mImageThumbnailPath;
    }

    public void setImageThumbnailPath(String imageThumbnailPath) {
        mImageThumbnailPath = imageThumbnailPath;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getNetwork() {
        return mNetwork;
    }

    public void setNetwork(String network) {
        mNetwork = network;
    }

    public String getStartDate() {
        return mStartDate;
    }

    public void setStartDate(String startDate) {
        mStartDate = startDate;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
