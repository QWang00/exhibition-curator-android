package com.northcoders.exhibition_curator_android.ui.my_collections;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.northcoders.exhibition_curator_android.databinding.FragmentCollectionsBinding;


public class MyCollectionsFragment extends Fragment {

    private FragmentCollectionsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MyCollectionsViewModel myCollectionsViewModel =
                new ViewModelProvider(this).get(MyCollectionsViewModel.class);

        binding = FragmentCollectionsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textCollections;
        myCollectionsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}