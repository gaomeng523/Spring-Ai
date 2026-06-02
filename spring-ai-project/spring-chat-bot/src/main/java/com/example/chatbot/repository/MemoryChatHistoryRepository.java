package com.example.chatbot.repository;

import com.example.chatbot.entity.ChatInfo;
import org.springframework.stereotype.Repository;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class MemoryChatHistoryRepository implements ChatHistoryRepository{

    private final Map<String, String> chatHistory = new LinkedHashMap<>();
    @Override
    public void save(String prompt, String chatId) {
        chatHistory.put(chatId, prompt); // ⾃动覆盖相同 chatId，但保持顺序
    }

    @Override
    public List<ChatInfo> getChats() {
        return chatHistory.entrySet().stream()
                .map(entry -> new ChatInfo(entry.getValue(), entry.getKey()))
                .collect(Collectors.toList());
    }

    @Override
    public void clearChatId(String chatId) {
        chatHistory.remove(chatId);
        return;
    }


}
