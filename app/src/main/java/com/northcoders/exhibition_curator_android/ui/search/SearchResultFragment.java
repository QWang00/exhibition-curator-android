package com.northcoders.exhibition_curator_android.ui.search;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import com.bumptech.glide.Glide;
import com.northcoders.exhibition_curator_android.R;
import com.northcoders.exhibition_curator_android.model.Artwork;
import java.util.List;

public class SearchResultFragment extends Fragment {
    private ArtworkViewModel artworkViewModel;
    private LinearLayout artworkContainer;
    private int currentPage = 1;
    private String museum, keyword, artist;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);

        artworkContainer = view.findViewById(R.id.artwork_container);

        LinearLayout prevButton = view.findViewById(R.id.prev_button);
        LinearLayout nextButton = view.findViewById(R.id.next_button);

        artworkViewModel = new ViewModelProvider(this).get(ArtworkViewModel.class);

        // Get search parameters
        Bundle args = getArguments();
        if (args != null) {
            museum = args.getString("museum");
            keyword = args.getString("keyword");
            artist = args.getString("artist");
            currentPage = args.getInt("page", 1);

            // Perform search
            artworkViewModel.searchArtworks(museum, keyword, artist, currentPage);
        }

        // Observe artwork data
        artworkViewModel.getArtworksLiveData().observe(getViewLifecycleOwner(), artworks -> {
            if (artworks != null) {
                displayArtworks(artworks);
            }
        });

        // Handle pagination
        nextButton.setOnClickListener(v -> {
            currentPage = artworkViewModel.getCurrentPageLiveData().getValue() + 1;
            artworkViewModel.searchArtworks(museum, keyword, artist, currentPage);
        });

        prevButton.setOnClickListener(v -> {
            if (currentPage > 1) {
                currentPage--;
                artworkViewModel.searchArtworks(museum, keyword, artist, currentPage);
            }
        });

        return view;
    }

    private void displayArtworks(List<Artwork> artworks) {
        artworkContainer.removeAllViews(); // Clear previous results
        if (artworks == null || artworks.isEmpty()) {
            TextView emptyView = new TextView(requireContext());
            emptyView.setText("No artworks found");
            emptyView.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary_accent));
            emptyView.setTextSize(23);
            emptyView.setGravity(Gravity.CENTER);
            artworkContainer.addView(emptyView);
            return;
        }

        for (Artwork artwork : artworks) {
            View artworkView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_artwork, artworkContainer, false);

            TextView preview = artworkView.findViewById(R.id.artwork_preview);
            ImageView image = artworkView.findViewById(R.id.artwork_image);

            preview.setText(artwork.getPreview());

            Glide.with(requireContext())
                    .load(artwork.getImageUrl())
                    .into(image);

            artworkView.setOnClickListener(v -> {
                // Pass artwork ID and museum name to ArtworkDetailsFragment
                Bundle bundle = new Bundle();
                bundle.putString("sourceArtworkId", artwork.getSourceArtworkId());
                bundle.putString("museumName", museum);
                Navigation.findNavController(v).navigate(R.id.navigation_artwork_details, bundle);
            });

            artworkContainer.addView(artworkView);
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // New Search click handler
        view.findViewById(R.id.new_search).setOnClickListener(v -> {
            NavOptions options = new NavOptions.Builder()
                    .setPopUpTo(R.id.navigation_search, true)
                    .build();

            Navigation.findNavController(v).navigate(
                    R.id.action_search_result_to_navigation_search,
                    null,
                    options
            );
        });
    }

}
