package com.example.tvshowsapp.network;

import com.example.tvshowsapp.response.ResponseTVShow;
import com.example.tvshowsapp.response.ResponseTVShowDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("most-popular")
    Call<ResponseTVShow> getMostPopularTVShow(@Query("page") int page);

    @GET("show-details")
    Call<ResponseTVShowDetail> getTVShoeDetails(@Query("q") String tvShowId);

}
