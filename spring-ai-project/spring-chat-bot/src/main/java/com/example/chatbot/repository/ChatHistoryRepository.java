package com.example.chatbot.repository;

import com.example.chatbot.entity.ChatInfo;

import java.util.List;

public interface ChatHistoryRepository {
    /**
     * 保存会话记录
     */
    void save(String prompt, String chatId);
    /**
     * 获取会话ID列表
     * @return 会话ID列表
     */
    List<ChatInfo> getChats();
    /**
     * 删除会话ID
     * @param chatId
     */
    void clearChatId(String chatId);
}
