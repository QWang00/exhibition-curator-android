package com.northcoders.exhibition_curator_android.ui.collections;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.northcoders.exhibition_curator_android.R;
import java.util.ArrayList;

public class CollectionFragment extends Fragment {
    private CollectionViewModel collectionViewModel;
    private RecyclerView collectionsRecyclerView;
    private CollectionAdapter collectionAdapter;
    private ImageView plusIcon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collections, container, false);

        collectionViewModel = new ViewModelProvider(this).get(CollectionViewModel.class);
        collectionsRecyclerView = view.findViewById(R.id.collections_recycler_view);
        plusIcon = view.findViewById(R.id.plus_icon);

        collectionsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        collectionAdapter = new CollectionAdapter(new ArrayList<>());
        collectionsRecyclerView.setAdapter(collectionAdapter);

        plusIcon.setOnClickListener(v -> showCreateCollectionBottomSheet());

        fetchCollections();

        return view;
    }



    private void showCreateCollectionBottomSheet() {
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        View sheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_create_collection, null);
        dialog.setContentView(sheetView);

        TextInputEditText collectionNameInput = sheetView.findViewById(R.id.collection_name_input);
        View createButton = sheetView.findViewById(R.id.create_button);

        createButton.setOnClickListener(v -> {
            String name = collectionNameInput.getText().toString().trim();
            if (!name.isEmpty()) {
                createNewCollection(name, dialog);
            } else {
                Toast.makeText(getContext(), "Please enter a collection name", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    private void createNewCollection(String name, BottomSheetDialog dialog) {
        collectionViewModel.createCollection(name).observe(getViewLifecycleOwner(), newCollection -> {
            if (newCollection != null) {
                Toast.makeText(getContext(), "Created Successfully", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                fetchCollections();
            } else {
                Toast.makeText(getContext(), "Failed to create collection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchCollections() {
        collectionViewModel.fetchAllCollections().observe(getViewLifecycleOwner(), collections -> {
            if (collections != null) {
                collectionAdapter.setCollectionList(collections);

                collectionAdapter.setOnItemClickListener(collection -> {
                    Bundle args = new Bundle();
                    args.putLong("collectionId", collection.getId());

                    Navigation.findNavController(requireView()).navigate(
                            R.id.action_collections_to_collection_detail,
                            args
                    );
                });
            } else {
                Toast.makeText(getContext(), "Failed to load collections", Toast.LENGTH_SHORT).show();
            }
        });
    }
}