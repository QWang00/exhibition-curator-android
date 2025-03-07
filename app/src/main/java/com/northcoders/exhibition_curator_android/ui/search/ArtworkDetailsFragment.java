package com.northcoders.exhibition_curator_android.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.bumptech.glide.Glide;
import com.northcoders.exhibition_curator_android.R;
import com.northcoders.exhibition_curator_android.model.Artwork;

public class ArtworkDetailsFragment extends Fragment {
    private ArtworkViewModel artworkViewModel;
    private String sourceArtworkId, museumName;
    private TextView previewText, descriptionText;
    private ImageView artworkImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artwork_detail, container, false);

        // Initialize UI elements
        previewText = view.findViewById(R.id.artwork_preview);
        descriptionText = view.findViewById(R.id.artwork_description);
        artworkImage = view.findViewById(R.id.artwork_image);

        // Retrieve passed arguments
        if (getArguments() != null) {
            sourceArtworkId = getArguments().getString("sourceArtworkId");
            museumName = getArguments().getString("museumName");
        }

        // Initialize ViewModel
        artworkViewModel = new ViewModelProvider(this).get(ArtworkViewModel.class);

        // Fetch artwork details from API
        artworkViewModel.getArtworkDetails(museumName, sourceArtworkId);

        // Observe LiveData and update UI
        artworkViewModel.getArtworkDetailsLiveData().observe(getViewLifecycleOwner(), artwork -> {
            if (artwork != null) {
                updateUI(artwork);
            }
        });

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.search_list_container).setOnClickListener(v -> {
            Navigation.findNavController(v).navigateUp();
        });
    }

}
