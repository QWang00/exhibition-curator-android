package com.northcoders.exhibition_curator_android.service.openai.model;

import java.util.List;

public class ChatRequest {
    private String model;
    private List<Message> messages;
    private int max_tokens;

    public ChatRequest(String model, List<Message> messages, int maxTokens) {
        this.model = model; // e.g. "gpt-3.5-turbo"
        this.messages = messages;
        this.max_tokens = maxTokens;
    }

    public static class Message {
        private String role;   // "user", "system", or "assistant"
        private String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }
}
