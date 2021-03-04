package com.example.tvshowsapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tvshowsapp.R;
import com.example.tvshowsapp.adapters.AdapterEpisodes;
import com.example.tvshowsapp.adapters.AdapterImageSlider;

import com.example.tvshowsapp.databinding.ActivityTVShowDetailsBinding;
import com.example.tvshowsapp.databinding.LayoutEpisodesBottomSheetBinding;
import com.example.tvshowsapp.models.ModelTvShow;
import com.example.tvshowsapp.utilities.TempDataHolder;
import com.example.tvshowsapp.viewmodels.ViewModelTVShowDetail;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Locale;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TVShowDetailsActivity extends AppCompatActivity {

    private ActivityTVShowDetailsBinding tvShowDetailsBinding;
    private ViewModelTVShowDetail viewModelTVShowDetail;
    private BottomSheetDialog episodeBottomSheetDialog;
    private LayoutEpisodesBottomSheetBinding layoutEpisodesBottomSheetBinding;
    private ModelTvShow tvShow;
    private Boolean isTVShowAvailableInWatchlist = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvShowDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_t_v_show_details);
        doInitialization();
    }

    private void doInitialization() {
        viewModelTVShowDetail = ViewModelProviders.of(this).get(ViewModelTVShowDetail.class);
        tvShowDetailsBinding.imageback.setOnClickListener(view -> onBackPressed());
        tvShow = (ModelTvShow) getIntent().getSerializableExtra("tvShow");
        checkTVShowInWatchlist();
        getTVShowDetails();
    }

    private void checkTVShowInWatchlist() {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(viewModelTVShowDetail.getTvShowFromWatchlist(String.valueOf(tvShow.getId()))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tvShow -> {
                    isTVShowAvailableInWatchlist = true;
                    tvShowDetailsBinding.imageWatchList.setImageResource(R.drawable.ic_added);
                    compositeDisposable.dispose();
                }));
    }

    private void getTVShowDetails() {
        tvShowDetailsBinding.setIsLoading(true);
        String tvShowId = String.valueOf(tvShow.getId());
        viewModelTVShowDetail.getTVShowDetails(tvShowId).observe(this, responseTVShowDetail -> {
            tvShowDetailsBinding.setIsLoading(false);
            if (responseTVShowDetail.getModelTvShowDetail() != null) {
                if (responseTVShowDetail.getModelTvShowDetail().getmPictures() != null) {
                    loadImageSlider(responseTVShowDetail.getModelTvShowDetail().getmPictures());
                }
                tvShowDetailsBinding.setTvShowImageURL(responseTVShowDetail.getModelTvShowDetail().getmImagePath());
                tvShowDetailsBinding.imageTVShow.setVisibility(View.VISIBLE);

                tvShowDetailsBinding.setDescription(String.valueOf(
                        HtmlCompat.fromHtml(responseTVShowDetail.getModelTvShowDetail().getmDescription(),
                                HtmlCompat.FROM_HTML_MODE_LEGACY)
                ));
                tvShowDetailsBinding.textDescription.setVisibility(View.VISIBLE);
                tvShowDetailsBinding.textReadMore.setVisibility(View.VISIBLE);
                tvShowDetailsBinding.textReadMore.setOnClickListener(view -> {
                    if (tvShowDetailsBinding.textReadMore.getText().toString().equals("Read More")) {
                        tvShowDetailsBinding.textDescription.setMaxLines(Integer.MAX_VALUE);
                        tvShowDetailsBinding.textDescription.setEllipsize(null);
                        tvShowDetailsBinding.textReadMore.setText(R.string.read_less);
                    } else {
                        tvShowDetailsBinding.textDescription.setMaxLines(4);
                        tvShowDetailsBinding.textDescription.setEllipsize(TextUtils.TruncateAt.END);
                        tvShowDetailsBinding.textReadMore.setText(R.string.read_more);
                    }
                });
                tvShowDetailsBinding.setRating(String.format(Locale.getDefault(), "%.2f", Double.parseDouble(
                        responseTVShowDetail.getModelTvShowDetail().getmRating())));
                if (responseTVShowDetail.getModelTvShowDetail().getmGenres() != null) {
                    tvShowDetailsBinding.setGenre(responseTVShowDetail.getModelTvShowDetail().getmGenres()[0]);
                } else {
                    tvShowDetailsBinding.setGenre("N/A");
                }
                tvShowDetailsBinding.setRuntime(String.valueOf(responseTVShowDetail.getModelTvShowDetail().getmRuntime()
                        + " min"));
                tvShowDetailsBinding.viewDivider1.setVisibility(View.VISIBLE);
                tvShowDetailsBinding.layoutMisc.setVisibility(View.VISIBLE);
                tvShowDetailsBinding.viewDivider2.setVisibility(View.VISIBLE);
                tvShowDetailsBinding.buttonWebsite.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(responseTVShowDetail.getModelTvShowDetail().getmUrl()));
                    startActivity(intent);
                });
                tvShowDetailsBinding.buttonWebsite.setVisibility(View.VISIBLE);
                tvShowDetailsBinding.buttonEpisodes.setVisibility(View.VISIBLE);
                tvShowDetailsBinding.buttonEpisodes.setOnClickListener(v -> {
                    if (episodeBottomSheetDialog == null) {
                        episodeBottomSheetDialog = new BottomSheetDialog(TVShowDetailsActivity.this);
                        layoutEpisodesBottomSheetBinding = DataBindingUtil.inflate(LayoutInflater
                                        .from(TVShowDetailsActivity.this),
                                R.layout.layout_episodes_bottom_sheet,
                                findViewById(R.id.episodes_container), false);
                        episodeBottomSheetDialog.setContentView(layoutEpisodesBottomSheetBinding.getRoot());
                        layoutEpisodesBottomSheetBinding.episodesRecyclerView.setAdapter(
                                new AdapterEpisodes(responseTVShowDetail.getModelTvShowDetail().getmEpisodes())
                        );
                        layoutEpisodesBottomSheetBinding.textTitle.setText(
                                String.format("Episodes | %s", tvShow.getName()));
                        layoutEpisodesBottomSheetBinding.imageClose.setOnClickListener(v1 ->
                                episodeBottomSheetDialog.dismiss());
                    }
                    FrameLayout frameLayout = episodeBottomSheetDialog.findViewById(
                            com.google.android.material.R.id.design_bottom_sheet
                    );
                    if (frameLayout != null) {
                        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(frameLayout);
                        bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                    episodeBottomSheetDialog.show();
                });
                tvShowDetailsBinding.imageWatchList.setOnClickListener(v -> {
                            CompositeDisposable compositeDisposable = new CompositeDisposable();
                            if (isTVShowAvailableInWatchlist) {
                                compositeDisposable.add(viewModelTVShowDetail.removeTvShowFromWatchlist(tvShow)
                                        .subscribeOn(Schedulers.computation())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(() -> {
                                            TempDataHolder.IS_WATCHLIST_UPDATED = true;
                                            isTVShowAvailableInWatchlist = false;
                                            tvShowDetailsBinding.imageWatchList.setImageResource(R.drawable.ic_watch_list);
                                            Toast.makeText(getApplicationContext(), "Removed from watchlist", Toast.LENGTH_SHORT).show();
                                            compositeDisposable.dispose();
                                        }));
                            } else {
                                compositeDisposable.add(viewModelTVShowDetail.addToWatchlist(tvShow)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(() -> {
                                            TempDataHolder.IS_WATCHLIST_UPDATED = true;
                                            tvShowDetailsBinding.imageWatchList.setImageResource(R.drawable.ic_added);
                                            Toast.makeText(getApplicationContext(), "Added to watchlist", Toast.LENGTH_SHORT).show();
                                            compositeDisposable.dispose();
                                        }));
                            }
                        }
                );


                tvShowDetailsBinding.imageWatchList.setVisibility(View.VISIBLE);
                loadBasicTVShowsDetails();
            }
        });

    }

    private void loadImageSlider(String[] sliderImages) {
        tvShowDetailsBinding.sliderViewPager.setOffscreenPageLimit(1);
        tvShowDetailsBinding.sliderViewPager.setAdapter(new AdapterImageSlider(sliderImages));
        tvShowDetailsBinding.sliderViewPager.setVisibility(View.VISIBLE);
        tvShowDetailsBinding.viewFadingEdge.setVisibility(View.VISIBLE);
        setupSlidersIndicators(sliderImages.length);
        tvShowDetailsBinding.sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentsliderIndicator(position);
            }
        });
    }

    private void setupSlidersIndicators(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                    R.drawable.background_slider_indicator_noactive));
            indicators[i].setLayoutParams(layoutParams);
            tvShowDetailsBinding.layoutSliderIndicators.addView(indicators[i]);
        }
        tvShowDetailsBinding.layoutSliderIndicators.setVisibility(View.VISIBLE);
        setCurrentsliderIndicator(0);
    }

    private void setCurrentsliderIndicator(int position) {
        int childcount = tvShowDetailsBinding.layoutSliderIndicators.getChildCount();
        for (int i = 0; i < childcount; i++) {
            ImageView imageView = (ImageView) tvShowDetailsBinding.layoutSliderIndicators.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext()
                        , R.drawable.background_slider_indicator_active));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.background_slider_indicator_noactive));
            }
        }
    }

    private void loadBasicTVShowsDetails() {
        tvShowDetailsBinding.setTvShowName(tvShow.getName());
        tvShowDetailsBinding.setNetworkCountry(tvShow.getNetwork() +
                " (" + tvShow.getCountry() + ")");
        tvShowDetailsBinding.setStatus(tvShow.getStatus());
        tvShowDetailsBinding.setStartedDate(tvShow.getStartDate());
        tvShowDetailsBinding.textName.setVisibility(View.VISIBLE);
        tvShowDetailsBinding.textNetworkCountry.setVisibility(View.VISIBLE);
        tvShowDetailsBinding.textStatus.setVisibility(View.VISIBLE);
        tvShowDetailsBinding.textStarted.setVisibility(View.VISIBLE);

    }
}