package com.northcoders.exhibition_curator_android.ui.collections;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.northcoders.exhibition_curator_android.model.Collection;
import com.northcoders.exhibition_curator_android.repository.CollectionRepository;
import java.util.List;

public class CollectionViewModel extends ViewModel {
    private final CollectionRepository collectionRepository;

    public CollectionViewModel() {
        this.collectionRepository = new CollectionRepository();
    }

    public LiveData<List<Collection>> fetchAllCollections() {
        return collectionRepository.fetchAllCollections();
    }

    public LiveData<Collection> createCollection(String name) {
        return collectionRepository.createCollection(name);
    }

}
