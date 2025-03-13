package com.northcoders.exhibition_curator_android.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.northcoders.exhibition_curator_android.R;
import com.northcoders.exhibition_curator_android.model.Artwork;
import com.northcoders.exhibition_curator_android.model.Collection;
import com.northcoders.exhibition_curator_android.ui.collections.CollectionAdapter;
import com.northcoders.exhibition_curator_android.ui.collections.CollectionViewModel;
import java.util.ArrayList;

public class ArtworkDetailsFragment extends Fragment {
    private ArtworkViewModel artworkViewModel;
    private CollectionViewModel collectionViewModel;
    private String sourceArtworkId, museumName;
    private TextView previewText, descriptionText;
    private ImageView artworkImage, heartIcon;

    private boolean isFromLocalDatabase = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artwork_detail, container, false);

        previewText = view.findViewById(R.id.artwork_preview);
        descriptionText = view.findViewById(R.id.artwork_description);
        artworkImage = view.findViewById(R.id.artwork_image);
        heartIcon = view.findViewById(R.id.heart_icon);

        if (getArguments() != null) {
            sourceArtworkId = getArguments().getString("sourceArtworkId");
            museumName = getArguments().getString("museumName");
        }

        collectionViewModel = new ViewModelProvider(this).get(CollectionViewModel.class);
        artworkViewModel = new ViewModelProvider(requireActivity()).get(ArtworkViewModel.class);

        artworkViewModel.getIsSavedLiveData().observe(getViewLifecycleOwner(), isSaved -> {
            updateHeartIcon(isSaved);
        });

        artworkViewModel.getArtworkDetailsLiveData().observe(getViewLifecycleOwner(), artwork -> {
            if (artwork != null) updateUI(artwork);
        });

        heartIcon.setOnClickListener(v -> {
            if (Boolean.TRUE.equals(artworkViewModel.getIsSavedLiveData().getValue())) {
                removeArtworkFromCollection();
            } else {
                showCollectionSelectionBottomSheet();
            }
        });

        artworkViewModel.getArtworkDetails(museumName, sourceArtworkId);
        checkIfArtworkIsSaved();

        return view;
    }

    private void updateUI(Artwork artwork) {
        previewText.setText(artwork.getPreview());
        descriptionText.setText(artwork.getDescription());

        Glide.with(requireContext())
                .load(artwork.getImageUrl())
                .placeholder(R.drawable.placeholder_image)
                .into(artworkImage);
    }

    private void checkIfArtworkIsSaved() {
        collectionViewModel.fetchAllCollections().observe(getViewLifecycleOwner(), collections -> {
            boolean found = false;
            for (Collection collection : collections) {
                if (collection.getArtworks() != null) {
                    for (Artwork artwork : collection.getArtworks()) {
                        if (artwork.getSourceArtworkId().equals(sourceArtworkId)) {
                            artworkViewModel.setSavedCollectionId(collection.getId());
                            artworkViewModel.setArtworkId(artwork.getId());
                            artworkViewModel.setIsSaved(true);
                            isFromLocalDatabase = true;
                            found = true;
                            break;
                        }
                    }
                }
                if (found) break;
            }
            if (!found) {
                artworkViewModel.setIsSaved(false);
                artworkViewModel.setSavedCollectionId(null);
                artworkViewModel.setArtworkId(null);
                isFromLocalDatabase = false;
            }
        });
    }


    private void showCollectionSelectionBottomSheet() {
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        View sheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_choose_collection, null);
        dialog.setContentView(sheetView);

        RecyclerView recyclerView = sheetView.findViewById(R.id.collection_recycler_view);
        TextView cancelButton = sheetView.findViewById(R.id.cancel_button);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        CollectionAdapter adapter = new CollectionAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        collectionViewModel.fetchAllCollections().observe(getViewLifecycleOwner(), collections -> {
            if (collections != null) {
                adapter.setCollectionList(collections);
            }
        });

        adapter.setOnItemClickListener(collection -> {
            saveArtworkToCollection(collection.getId());
            dialog.dismiss();
        });

        cancelButton.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }


    private void removeArtworkFromCollection() {
        Long collectionId = artworkViewModel.getSavedCollectionIdLiveData().getValue();
        Long artworkId = artworkViewModel.getArtworkIdLiveData().getValue();

        if (collectionId == null || artworkId == null) return;

        collectionViewModel.removeArtworkFromCollection(collectionId, artworkId)
                .observe(getViewLifecycleOwner(), success -> {
                    if (success) {
                        artworkViewModel.setIsSaved(false);
                        artworkViewModel.setSavedCollectionId(null);
                        artworkViewModel.setArtworkId(null);
                        updateHeartIcon(false);
                        Toast.makeText(getContext(), "Removed successfully", Toast.LENGTH_SHORT).show();
                    }  else {
            Toast.makeText(getContext(), "Failed to remove artwork.", Toast.LENGTH_SHORT).show();
        }
                });
    }

    private void saveArtworkToCollection(Long collectionId) {
        if (isFromLocalDatabase) {
            Long artworkId = artworkViewModel.getArtworkIdLiveData().getValue();
            if (artworkId == null) return;

            collectionViewModel.addArtworkToCollectionFromLocal(collectionId, artworkId)
                    .observe(getViewLifecycleOwner(), updatedCollection -> {
                        if (updatedCollection != null) {
                            artworkViewModel.setIsSaved(true);
                            artworkViewModel.setSavedCollectionId(collectionId);
                            Toast.makeText(getContext(), "Saved successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            collectionViewModel.addArtworkToCollection(collectionId, sourceArtworkId, museumName)
                    .observe(getViewLifecycleOwner(), updatedCollection -> {
                        if (updatedCollection != null) {
                            artworkViewModel.setIsSaved(true);
                            artworkViewModel.setSavedCollectionId(collectionId);

                            for (Artwork artwork : updatedCollection.getArtworks()) {
                                if (artwork.getSourceArtworkId().equals(sourceArtworkId)) {
                                    artworkViewModel.setArtworkId(artwork.getId());
                                    break;
                                }
                            }
                            Toast.makeText(getContext(), "Saved successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void updateHeartIcon(boolean isSaved) {
        if (isFromLocalDatabase) {
            // Artwork is from the local database
            if (isSaved) {
                // Artwork is saved: show filled heart and disable the icon
                heartIcon.setImageResource(R.drawable.ic_heart_filled);
                heartIcon.setEnabled(false); // Disable the heart icon
            } else {
                // Artwork is removed: show unfilled heart and disable the icon
                heartIcon.setImageResource(R.drawable.ic_heart_outline);
                heartIcon.setEnabled(false); // Disable the heart icon
            }
        } else {
            // Artwork is from the external API
            heartIcon.setImageResource(isSaved ? R.drawable.ic_heart_filled : R.drawable.ic_heart_outline);
            heartIcon.setEnabled(true); // Enable the heart icon
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View searchListContainer = view.findViewById(R.id.search_list_container);
        if (searchListContainer != null) {
            searchListContainer.setOnClickListener(v -> {
                Navigation.findNavController(v).navigateUp();
            });
        }
    }

}