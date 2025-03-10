package com.northcoders.exhibition_curator_android.service;

import com.northcoders.exhibition_curator_android.model.Artwork;
import com.northcoders.exhibition_curator_android.model.ArtworkResponse;

import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ArtworkApiService {

    @GET("search-results/{museum}")
    Call<ArtworkResponse> searchArtworks(
            @Path("museum") String museum,
            @Query("keyword") String keyword,
            @Query("artist") String artist,
            @Query("page") int page
    );


    @GET("{museum}/artwork/{sourceId}")
    Call<Artwork> getArtworkDetails(
            @Path("museum") String museum,
            @Path("sourceId") String sourceId
    );

}
