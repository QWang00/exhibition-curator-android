package com.northcoders.exhibition_curator_android.service;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit = null;
    private final static String BASE_URL = "http://10.0.2.2:8080/api/v1/";

    // Initialize Retrofit instance if not already created
    private static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            // Add logging interceptor for debugging API requests
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    // Provide API service for Artworks
    public static ArtworkApiService getArtworkService() {
        return getRetrofitInstance().create(ArtworkApiService.class);
    }

    // Provide API service for Exhibitions (Collections)
    public static CollectionApiService getCollectionService() {
        return getRetrofitInstance().create(CollectionApiService.class);
    }
}
