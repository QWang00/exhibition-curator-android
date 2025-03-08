package com.northcoders.exhibition_curator_android.service;

import com.northcoders.exhibition_curator_android.model.ChatRequest;
import com.northcoders.exhibition_curator_android.model.ChatResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OpenAiApiService {
    @POST("chat/completions")
    Call<ChatResponse> getChatCompletion(
            @Header("Authorization") String bearerToken,
            @Body ChatRequest chatRequest
    );
}
