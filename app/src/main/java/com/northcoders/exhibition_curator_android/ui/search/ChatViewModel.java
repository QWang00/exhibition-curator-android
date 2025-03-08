package com.northcoders.exhibition_curator_android.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.northcoders.exhibition_curator_android.BuildConfig;
import com.northcoders.exhibition_curator_android.model.ChatRequest;
import com.northcoders.exhibition_curator_android.model.ChatRequest.Message;
import com.northcoders.exhibition_curator_android.model.ChatResponse;
import com.northcoders.exhibition_curator_android.service.OpenAiApiService;
import com.northcoders.exhibition_curator_android.service.OpenAiRetrofitInstance;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatViewModel extends ViewModel {
    private final MutableLiveData<String> chatResponseLiveData = new MutableLiveData<>();
    private final OpenAiApiService openAiApiService;

    public ChatViewModel() {
        openAiApiService = OpenAiRetrofitInstance.getInstance().create(OpenAiApiService.class);
    }

    public LiveData<String> getChatResponseLiveData() {
        return chatResponseLiveData;
    }

    public void callChatGPT(String userPrompt) {
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message("user", userPrompt));

        ChatRequest request = new ChatRequest("gpt-3.5-turbo", messages, 100);

        openAiApiService.getChatCompletion(
                "Bearer " + BuildConfig.OPENAI_API_KEY,
                request
        ).enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ChatResponse chatResponse = response.body();
                    if (!chatResponse.getChoices().isEmpty()) {
                        String content = chatResponse.getChoices().get(0).getMessage().getContent();
                        chatResponseLiveData.postValue(content);
                    } else {
                        chatResponseLiveData.postValue("No response from AI");
                    }
                } else {
                    chatResponseLiveData.postValue("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                chatResponseLiveData.postValue("Failed: " + t.getMessage());
            }
        });
    }
}
