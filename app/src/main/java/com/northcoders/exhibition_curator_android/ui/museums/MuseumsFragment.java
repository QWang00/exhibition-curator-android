package com.northcoders.exhibition_curator_android.ui.museums;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.northcoders.exhibition_curator_android.databinding.FragmentMuseumsBinding;

public class MuseumsFragment extends Fragment {

    private FragmentMuseumsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MuseumsViewModel museumsViewModel =
                new ViewModelProvider(this).get(MuseumsViewModel.class);

        binding = FragmentMuseumsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textMuseums;
        museumsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}