package com.exemple.demo.controller;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {

    // 这里必须用通用的 ChatModel，不能用 OpenAiChatModel！
    @Autowired
    private ChatModel chatModel;

    @GetMapping("/generate")
    public String generate(@RequestParam String message){
        return chatModel.call(message);
    }
}