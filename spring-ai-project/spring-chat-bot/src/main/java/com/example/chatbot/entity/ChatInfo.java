package com.example.chatbot.entity;

import lombok.Data;

@Data
public class ChatInfo {
    private String title;
    private String chatId;
    public ChatInfo(String title, String chatId) {
        this.title = title==null?"⽆标题": title.length()>15 ?
                title.substring(0,15):title;
        this.chatId = chatId;
    }
}
