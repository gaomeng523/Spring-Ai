package com.bit.ollama.controller;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/chat")
@RestController
public class ChatClientController {
    @Autowired
    public ChatClient chatClient;

    @RequestMapping("/role")
    public String role(String prompt){
        return chatClient.prompt().user(prompt).call().content();
    }
}
