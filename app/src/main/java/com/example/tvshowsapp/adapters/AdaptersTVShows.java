package com.example.tvshowsapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvshowsapp.R;
import com.example.tvshowsapp.databinding.ItemContainerTvShowBinding;
import com.example.tvshowsapp.listeners.ListenerTVShow;
import com.example.tvshowsapp.models.ModelTvShow;

import java.util.List;

public class AdaptersTVShows extends RecyclerView.Adapter<AdaptersTVShows.TVShowViewHolder>{

    private List<ModelTvShow> tvShows;
    private LayoutInflater layoutInflater;
    private ListenerTVShow listenerTVShow;

    public AdaptersTVShows(List<ModelTvShow> tvShows, ListenerTVShow listenerTVShow) {
        this.tvShows = tvShows;
        this.listenerTVShow = listenerTVShow;
    }

    @NonNull
    @Override
    public TVShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerTvShowBinding tvShowBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_container_tv_show,parent,false
        );
        return new TVShowViewHolder(tvShowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowViewHolder holder, int position) {
        holder.bindTVSows(tvShows.get(position));
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    class TVShowViewHolder extends RecyclerView.ViewHolder {

        private ItemContainerTvShowBinding itemContainerTvShowBinding;

        public TVShowViewHolder(@NonNull ItemContainerTvShowBinding itemContainerTvShowBinding) {
            super(itemContainerTvShowBinding.getRoot());
            this.itemContainerTvShowBinding = itemContainerTvShowBinding;
        }

        public void bindTVSows(ModelTvShow modelTvShow) {
            itemContainerTvShowBinding.setTvshows(modelTvShow);
            itemContainerTvShowBinding.executePendingBindings();
            itemContainerTvShowBinding.getRoot().setOnClickListener(
                    v -> listenerTVShow.onTVShowClicked(modelTvShow));
        }
    }
}
