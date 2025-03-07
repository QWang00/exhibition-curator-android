package com.northcoders.exhibition_curator_android.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.northcoders.exhibition_curator_android.R;

public class SearchFragment extends Fragment {
    private RadioGroup museumRadioGroup;
    private EditText keywordInput, artistInput;
    private ImageView btnSearchKeyword, btnSearchArtist;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        museumRadioGroup = view.findViewById(R.id.museum_radio_group);
        keywordInput = view.findViewById(R.id.search_keyword);
        artistInput = view.findViewById(R.id.search_artist);
        btnSearchKeyword = view.findViewById(R.id.btn_search_keyword);
        btnSearchArtist = view.findViewById(R.id.btn_search_artist);

        // Both buttons trigger the same search function
        btnSearchKeyword.setOnClickListener(v -> performSearch(v));
        btnSearchArtist.setOnClickListener(v -> performSearch(v));

        return view;
    }

    private void performSearch(View view) {
        int selectedMuseumId = museumRadioGroup.getCheckedRadioButtonId();
        String museumName = "";

        if (selectedMuseumId == R.id.radio_cleveland) {
            museumName = "cleveland";
        } else if (selectedMuseumId == R.id.radio_harvard) {
            museumName = "harvard";
        }

        String keyword = keywordInput.getText().toString().trim();
        String artist = artistInput.getText().toString().trim();

        if (!museumName.isEmpty()) {
            // Pass search parameters to SearchResultFragment
            Bundle bundle = new Bundle();
            bundle.putString("museum", museumName);
            bundle.putString("keyword", keyword);
            bundle.putString("artist", artist);
            bundle.putInt("page", 1);

            Navigation.findNavController(view).navigate(R.id.action_search_to_search_result, bundle);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Reset form fields
        museumRadioGroup.clearCheck();
        keywordInput.setText("");
        artistInput.setText("");
    }
}
