package com.northcoders.exhibition_curator_android.ui.search;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.northcoders.exhibition_curator_android.model.Artwork;
import com.northcoders.exhibition_curator_android.model.ArtworkResponse;
import com.northcoders.exhibition_curator_android.service.RetrofitInstance;
import com.northcoders.exhibition_curator_android.service.ArtworkApiService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtworkViewModel extends ViewModel {
    private final ArtworkApiService artworkApiService;
    private final MutableLiveData<List<Artwork>> artworksLiveData = new MutableLiveData<>();
    private final MutableLiveData<Artwork> artworkDetailsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> currentPageLiveData = new MutableLiveData<>();
    private String currentMuseum;
    private String currentKeyword;
    private String currentArtist;
    private final MutableLiveData<Boolean> isSavedLiveData = new MutableLiveData<>(false);
    private final MutableLiveData<Long> savedCollectionIdLiveData = new MutableLiveData<>();
    private final MutableLiveData<Long> artworkIdLiveData = new MutableLiveData<>();

    public ArtworkViewModel() {
        artworkApiService = RetrofitInstance.getArtworkService();
        currentPageLiveData.setValue(1);
    }

    public LiveData<List<Artwork>> getArtworksLiveData() {
        return artworksLiveData;
    }

    public LiveData<Integer> getCurrentPageLiveData() {
        return currentPageLiveData;
    }

    // New LiveData method for artwork details
    public LiveData<Artwork> getArtworkDetailsLiveData() {
        return artworkDetailsLiveData;
    }

    public void searchArtworks(String museum, String keyword, String artist, int page) {
        this.currentMuseum = museum;
        this.currentKeyword = keyword;
        this.currentArtist = artist;
        this.currentPageLiveData.setValue(page);

        artworkApiService.searchArtworks(museum, keyword, artist, page)
                .enqueue(new Callback<ArtworkResponse>() {
                    @Override
                    public void onResponse(Call<ArtworkResponse> call, Response<ArtworkResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            ArtworkResponse artworkResponse = response.body();
                            artworksLiveData.postValue(artworkResponse.getArtworks());
                        } else {
                            Log.e("API Error", "Response code: " + response.code());
                            artworksLiveData.postValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArtworkResponse> call, Throwable t) {
                        Log.e("API Failure", t.getMessage());
                        artworksLiveData.postValue(null);
                    }

                });
    }

    public void nextPage() {
        Integer currentPage = currentPageLiveData.getValue();
        if (currentPage != null) {
            searchArtworks(currentMuseum, currentKeyword, currentArtist, currentPage + 1);
        }
    }

    public void previousPage() {
        Integer currentPage = currentPageLiveData.getValue();
        if (currentPage != null && currentPage > 1) {
            searchArtworks(currentMuseum, currentKeyword, currentArtist, currentPage - 1);
        }
    }

    // New method to fetch artwork details
    public void getArtworkDetails(String museum, String sourceId) {
        artworkApiService.getArtworkDetails(museum, sourceId)
                .enqueue(new Callback<Artwork>() {
                    @Override
                    public void onResponse(Call<Artwork> call, Response<Artwork> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            artworkDetailsLiveData.postValue(response.body());
                        } else {
                            artworkDetailsLiveData.postValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<Artwork> call, Throwable t) {
                        artworkDetailsLiveData.postValue(null);
                    }
                });
    }

    public LiveData<Boolean> getIsSavedLiveData() {
        return isSavedLiveData;
    }

    public void setIsSaved(boolean saved) {
        isSavedLiveData.setValue(saved);
    }

    public LiveData<Long> getSavedCollectionIdLiveData() {
        return savedCollectionIdLiveData;
    }

    public void setSavedCollectionId(Long collectionId) {
        savedCollectionIdLiveData.setValue(collectionId);

    }

    public LiveData<Long> getArtworkIdLiveData() {
        return artworkIdLiveData;
    }

    public void setArtworkId(Long artworkId) {
        artworkIdLiveData.setValue(artworkId);
    }
}