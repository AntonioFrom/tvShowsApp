package com.example.tvshowsapp.network;

import com.example.tvshowsapp.response.ResponseTVShow;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("most-popular")
    Call<ResponseTVShow> getMostPopularTVShow(@Query("page") int page);

}
