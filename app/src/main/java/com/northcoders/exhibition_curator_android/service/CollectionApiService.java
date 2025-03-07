package com.northcoders.exhibition_curator_android.service;

import com.northcoders.exhibition_curator_android.model.Collection;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import java.util.List;

public interface CollectionApiService {


    @GET("api/v1/collections")
    Call<List<Collection>> getAllCollections();


    @GET("api/v1/collection/{id}")
    Call<Collection> getCollectionById(@Path("id") Long id);


    @POST("api/v1/collections")
    Call<Collection> createCollection(@Query("name") String name);


    @PUT("api/v1/collection/{id}/name")
    Call<Collection> updateCollectionName(@Path("id") Long id, @Query("newName") String newName);


    @POST("api/v1/collection/{id}/artworks")
    Call<Collection> addArtworkToCollection(
            @Path("id") Long collectionId,
            @Query("sourceArtworkId") String sourceArtworkId,
            @Query("museum") String museum
    );


    @DELETE("api/v1/collection/{collectionId}/artworks/{artworkId}")
    Call<Void> removeArtworkFromCollection(
            @Path("collectionId") Long collectionId,
            @Path("artworkId") Long artworkId
    );
}
