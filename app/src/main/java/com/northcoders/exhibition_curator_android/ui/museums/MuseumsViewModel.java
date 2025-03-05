package com.northcoders.exhibition_curator_android.ui.museums;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MuseumsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MuseumsViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}