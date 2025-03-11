package com.northcoders.exhibition_curator_android.ui.collections;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.northcoders.exhibition_curator_android.R;
import com.northcoders.exhibition_curator_android.model.Artwork;
import java.util.ArrayList;
import java.util.List;

public class ArtworkAdapter extends RecyclerView.Adapter<ArtworkAdapter.ViewHolder> {
    private List<Artwork> artworkList;

    public ArtworkAdapter() {
        this.artworkList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_artwork, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Artwork artwork = artworkList.get(position);
        holder.artworkPreview.setText(artwork.getPreview());


         Glide.with(holder.itemView.getContext())
              .load(artwork.getImageUrl())
              .into(holder.artworkImage);
    }

    @Override
    public int getItemCount() {
        return artworkList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView artworkImage;
        TextView artworkPreview;

        public ViewHolder(View view) {
            super(view);
            artworkImage = view.findViewById(R.id.artwork_image);
            artworkPreview = view.findViewById(R.id.artwork_preview);
        }
    }

    public void setArtworkList(List<Artwork> newList) {
        artworkList.clear();
        artworkList.addAll(newList);
        notifyDataSetChanged();
    }

}