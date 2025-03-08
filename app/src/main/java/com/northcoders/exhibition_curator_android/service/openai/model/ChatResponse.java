package com.northcoders.exhibition_curator_android.service.openai.model;

import java.util.List;

public class ChatResponse {
    private List<Choice> choices;

    public List<Choice> getChoices() {
        return choices;
    }

    public static class Choice {
        private int index;
        private Message message;
        // "finish_reason", etc.

        public int getIndex() { return index; }
        public Message getMessage() { return message; }

        public static class Message {
            private String role;
            private String content;

            public String getRole() { return role; }
            public String getContent() { return content; }
        }
    }
}
