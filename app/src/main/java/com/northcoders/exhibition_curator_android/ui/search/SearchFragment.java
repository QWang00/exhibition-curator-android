package com.northcoders.exhibition_curator_android.ui.search;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
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

        // Museum validation
        if (selectedMuseumId == -1) {
            showCustomToast("Please select a museum first!", Toast.LENGTH_SHORT);
            return;
        } else if (selectedMuseumId == R.id.radio_cleveland) {
            museumName = "cleveland";
        } else if (selectedMuseumId == R.id.radio_harvard) {
            museumName = "harvard";
        }

        String keyword = keywordInput.getText().toString().trim();
        String artist = artistInput.getText().toString().trim();

        // Search criteria validation
        if (keyword.isEmpty() && artist.isEmpty()) {
            showCustomToast("Please enter at least a keyword or artist name", Toast.LENGTH_LONG);
            return;
        }

        // Proceed with search
        Bundle bundle = new Bundle();
        bundle.putString("museum", museumName);
        bundle.putString("keyword", keyword);
        bundle.putString("artist", artist);
        bundle.putInt("page", 1);

        Navigation.findNavController(view).navigate(R.id.action_search_to_search_result, bundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Reset form fields
        museumRadioGroup.clearCheck();
        keywordInput.setText("");
        artistInput.setText("");
    }

    private void showCustomToast(String message, int duration) {
        // Inflate layout without attaching to parent
        View layout = getLayoutInflater().inflate(R.layout.custom_toast, null);

        TextView text = layout.findViewById(R.id.toast_text);
        text.setText(message);

        Toast toast = new Toast(requireContext());
        toast.setDuration(duration);
        toast.setGravity(Gravity.CENTER, 0, 0); // Center on screen
        toast.setView(layout);
        toast.show();
    }
}
