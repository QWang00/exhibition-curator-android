package com.northcoders.exhibition_curator_android.ui.collections;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.northcoders.exhibition_curator_android.R;
import com.northcoders.exhibition_curator_android.model.Artwork;
import com.northcoders.exhibition_curator_android.model.Collection;
import java.util.ArrayList;
import java.util.List;

public class CollectionDetailFragment extends Fragment {
    private CollectionViewModel collectionViewModel;
    private ArtworkAdapter artworkAdapter;
    private TextView collectionName;
    private EditText collectionNameEdit;
    private ImageView editIcon;
    private Long collectionId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            collectionId = getArguments().getLong("collectionId");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection_detail, container, false);

        collectionViewModel = new ViewModelProvider(this).get(CollectionViewModel.class);
        RecyclerView artworksRecyclerView = view.findViewById(R.id.artworks_recycler_view);
        collectionName = view.findViewById(R.id.collection_name);
        collectionNameEdit = view.findViewById(R.id.collection_name_edit);
        editIcon = view.findViewById(R.id.edit_icon);

        artworksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        artworkAdapter = new ArtworkAdapter();
        artworksRecyclerView.setAdapter(artworkAdapter);

        // Set the click listener for the artwork items
        artworkAdapter.setOnItemClickListener(artwork -> {
            Bundle args = new Bundle();
            args.putString("sourceArtworkId", artwork.getSourceArtworkId());
            args.putString("museumName", artwork.getMuseumName());

            Navigation.findNavController(view).navigate(
                    R.id.action_navigation_collection_detail_to_navigation_artwork_details,
                    args
            );
        });
        if (collectionId != null) {
            collectionViewModel.getCollectionById(collectionId).observe(getViewLifecycleOwner(), collection -> {
                if (collection != null) {
                    updateUI(collection);
                }
            });
        }

        setupEditToggle();
        return view;
    }

    private void updateUI(Collection collection) {
        collectionName.setText(collection.getName());
        List<Artwork> artworks = new ArrayList<>(collection.getArtworks());
        artworkAdapter.setArtworkList(artworks);
    }

    private void setupEditToggle() {
        editIcon.setOnClickListener(v -> toggleEditMode());

        collectionNameEdit.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                saveCollectionName();
                return true;
            }
            return false;
        });
    }

    private void toggleEditMode() {
        if (collectionName.getVisibility() == View.VISIBLE) {
            collectionName.setVisibility(View.GONE);
            collectionNameEdit.setVisibility(View.VISIBLE);
            collectionNameEdit.setText(collectionName.getText());
            collectionNameEdit.requestFocus();
            showKeyboard();
        } else {
            saveCollectionName();
        }
    }

    private void saveCollectionName() {
        String newName = collectionNameEdit.getText().toString().trim();
        if (!newName.isEmpty()) {
            collectionViewModel.updateCollectionName(collectionId, newName)
                    .observe(getViewLifecycleOwner(), updatedCollection -> {
                        if (updatedCollection != null) {
                            collectionName.setText(newName);
                            collectionName.setVisibility(View.VISIBLE);
                            collectionNameEdit.setVisibility(View.GONE);
                            hideKeyboard();
                        }
                    });
        }
    }

    private void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(collectionNameEdit, InputMethodManager.SHOW_IMPLICIT);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(collectionNameEdit.getWindowToken(), 0);
    }
}