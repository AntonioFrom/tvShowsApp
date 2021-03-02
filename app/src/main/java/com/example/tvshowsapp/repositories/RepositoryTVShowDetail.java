package com.example.tvshowsapp.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tvshowsapp.network.ApiClient;
import com.example.tvshowsapp.network.ApiService;
import com.example.tvshowsapp.response.ResponseTVShowDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryTVShowDetail {

    private ApiService apiService;

    public RepositoryTVShowDetail() {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<ResponseTVShowDetail> getTVShowDetails(String tvShowId) {
        MutableLiveData<ResponseTVShowDetail> data = new MutableLiveData<>();
        apiService.getTVShowDetails(tvShowId).enqueue(new Callback<ResponseTVShowDetail>() {
            @Override
            public void onResponse(@NonNull Call<ResponseTVShowDetail> call, @NonNull Response<ResponseTVShowDetail> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ResponseTVShowDetail> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
