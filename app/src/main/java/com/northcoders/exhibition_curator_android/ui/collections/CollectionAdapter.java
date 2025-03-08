package com.northcoders.exhibition_curator_android.ui.collections;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.northcoders.exhibition_curator_android.R;
import com.northcoders.exhibition_curator_android.model.Collection;
import java.util.List;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {
    private List<Collection> collectionList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Collection collection);
    }

    public CollectionAdapter(List<Collection> collectionList) {
        this.collectionList = collectionList;
    }

    public void setCollectionList(List<Collection> newList) {
        this.collectionList = newList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_collection, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Collection collection = collectionList.get(position);
        holder.collectionName.setText(collection.getName());
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(collection);
            }
        });
    }

    @Override
    public int getItemCount() {
        return collectionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView collectionName;
        ImageView arrowIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            collectionName = itemView.findViewById(R.id.collection_name);
            arrowIcon = itemView.findViewById(R.id.arrow_icon);
        }
    }
}
