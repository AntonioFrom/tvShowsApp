package com.example.tvshowsapp.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tvshowsapp.network.ApiClient;
import com.example.tvshowsapp.network.ApiService;
import com.example.tvshowsapp.response.ResponseTVShow;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryMostPopularTVShow {

    private ApiService apiService;

    public RepositoryMostPopularTVShow() {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<ResponseTVShow> getMostPopularTVShows(int page) {
        MutableLiveData<ResponseTVShow> data = new MutableLiveData<>();
        apiService.getMostPopularTVShow(page).enqueue(new Callback<ResponseTVShow>() {
            @Override
            public void onResponse(@NonNull Call<ResponseTVShow> call, @NonNull Response<ResponseTVShow> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ResponseTVShow> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
