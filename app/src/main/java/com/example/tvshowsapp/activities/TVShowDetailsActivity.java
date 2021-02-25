package com.example.tvshowsapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.example.tvshowsapp.R;
import com.example.tvshowsapp.databinding.ActivityTVShowDetailsBinding;
import com.example.tvshowsapp.viewmodels.ViewModelTVShowDetail;

public class TVShowDetailsActivity extends AppCompatActivity {

    private ActivityTVShowDetailsBinding tvShowDetailsBinding;
    private ViewModelTVShowDetail viewModelTVShowDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvShowDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_t_v_show_details);
        doInitialization();
    }

    private void doInitialization() {
        viewModelTVShowDetail = new ViewModelProvider(this).get(ViewModelTVShowDetail.class);
        getTVShowDetails();
    }

    private void getTVShowDetails() {
        tvShowDetailsBinding.setIsLoading(true);
        String tvShowId = String.valueOf(getIntent().getIntExtra("id", -1));
        viewModelTVShowDetail.getTVShowDetails(tvShowId).observe(this, responseTVShowDetail -> {
            tvShowDetailsBinding.setIsLoading(false);
            Toast.makeText(getApplicationContext(),
                    responseTVShowDetail.getModelTvShowDetail().getmUrl(), Toast.LENGTH_LONG).show();
        });
    }
}