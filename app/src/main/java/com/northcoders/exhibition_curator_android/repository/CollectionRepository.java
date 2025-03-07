package com.northcoders.exhibition_curator_android.repository;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.northcoders.exhibition_curator_android.model.Collection;
import com.northcoders.exhibition_curator_android.service.RetrofitInstance;
import com.northcoders.exhibition_curator_android.service.CollectionApiService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionRepository {
    private final CollectionApiService collectionApiService;

    public CollectionRepository() {
        this.collectionApiService = RetrofitInstance.getCollectionService(); // Retrieves the API service for exhibitions
    }

    // ✅ Fetch all exhibitions (collections)
    public LiveData<List<Collection>> fetchAllCollections() {
        MutableLiveData<List<Collection>> exhibitionsLiveData = new MutableLiveData<>();

        collectionApiService.getAllCollections().enqueue(new Callback<List<Collection>>() {
            @Override
            public void onResponse(Call<List<Collection>> call, Response<List<Collection>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    exhibitionsLiveData.postValue(response.body());
                } else {
                    Log.e("CollectionRepository", "Failed to fetch collections.");
                    exhibitionsLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Collection>> call, Throwable t) {
                Log.e("CollectionRepository", "API Error: " + t.getMessage());
                exhibitionsLiveData.postValue(null);
            }
        });

        return exhibitionsLiveData;
    }

    // ✅ Fetch a single exhibition (collection) by ID
    public LiveData<Collection> fetchCollectionById(Long id) {
        MutableLiveData<Collection> collectionLiveData = new MutableLiveData<>();

        collectionApiService.getCollectionById(id).enqueue(new Callback<Collection>() {
            @Override
            public void onResponse(Call<Collection> call, Response<Collection> response) {
                if (response.isSuccessful() && response.body() != null) {
                    collectionLiveData.postValue(response.body());
                } else {
                    Log.e("CollectionRepository", "Failed to fetch collection.");
                    collectionLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<Collection> call, Throwable t) {
                Log.e("CollectionRepository", "API Error: " + t.getMessage());
                collectionLiveData.postValue(null);
            }
        });

        return collectionLiveData;
    }

    // ✅ Create a new exhibition (collection)
    public LiveData<Collection> createCollection(String name) {
        MutableLiveData<Collection> newCollectionLiveData = new MutableLiveData<>();

        collectionApiService.createCollection(name).enqueue(new Callback<Collection>() {
            @Override
            public void onResponse(Call<Collection> call, Response<Collection> response) {
                if (response.isSuccessful() && response.body() != null) {
                    newCollectionLiveData.postValue(response.body());
                } else {
                    Log.e("CollectionRepository", "Failed to create collection.");
                    newCollectionLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<Collection> call, Throwable t) {
                Log.e("CollectionRepository", "API Error: " + t.getMessage());
                newCollectionLiveData.postValue(null);
            }
        });

        return newCollectionLiveData;
    }

    // ✅ Update an exhibition's (collection's) name
    public LiveData<Collection> updateCollectionName(Long id, String newName) {
        MutableLiveData<Collection> updatedCollectionLiveData = new MutableLiveData<>();

        collectionApiService.updateCollectionName(id, newName).enqueue(new Callback<Collection>() {
            @Override
            public void onResponse(Call<Collection> call, Response<Collection> response) {
                if (response.isSuccessful() && response.body() != null) {
                    updatedCollectionLiveData.postValue(response.body());
                } else {
                    Log.e("CollectionRepository", "Failed to update collection.");
                    updatedCollectionLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<Collection> call, Throwable t) {
                Log.e("CollectionRepository", "API Error: " + t.getMessage());
                updatedCollectionLiveData.postValue(null);
            }
        });

        return updatedCollectionLiveData;
    }

    // ✅ Add an artwork to a collection
    public LiveData<Collection> addArtworkToCollection(Long collectionId, String sourceArtworkId, String museum) {
        MutableLiveData<Collection> updatedCollectionLiveData = new MutableLiveData<>();

        collectionApiService.addArtworkToCollection(collectionId, sourceArtworkId, museum)
                .enqueue(new Callback<Collection>() {
                    @Override
                    public void onResponse(Call<Collection> call, Response<Collection> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            updatedCollectionLiveData.postValue(response.body());
                        } else {
                            Log.e("CollectionRepository", "Failed to add artwork.");
                            updatedCollectionLiveData.postValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<Collection> call, Throwable t) {
                        Log.e("CollectionRepository", "API Error: " + t.getMessage());
                        updatedCollectionLiveData.postValue(null);
                    }
                });

        return updatedCollectionLiveData;
    }

    // ✅ Remove an artwork from a collection
    public LiveData<Boolean> removeArtworkFromCollection(Long collectionId, Long artworkId) {
        MutableLiveData<Boolean> successLiveData = new MutableLiveData<>();

        collectionApiService.removeArtworkFromCollection(collectionId, artworkId)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            successLiveData.postValue(true);
                        } else {
                            Log.e("CollectionRepository", "Failed to remove artwork.");
                            successLiveData.postValue(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("CollectionRepository", "API Error: " + t.getMessage());
                        successLiveData.postValue(false);
                    }
                });

        return successLiveData;
    }
}
