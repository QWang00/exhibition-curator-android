package com.northcoders.exhibition_curator_android.ui.my_collections;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyCollectionsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MyCollectionsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is collections fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}