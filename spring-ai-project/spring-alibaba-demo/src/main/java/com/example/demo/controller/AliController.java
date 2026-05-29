package com.example.demo.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ali")
public class AliController {

    private static final String DEFAULT_PROMPT = "你是⼀个博学的智能聊天助⼿, 请根据⽤⼾提问回答！";
    private final ChatClient dashScopeChatClient;

    public AliController(ChatClient.Builder chatClientBuilder){
        this.dashScopeChatClient = chatClientBuilder
                .defaultSystem(DEFAULT_PROMPT)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    @GetMapping("/chat")
    public String chat(String message){
        return dashScopeChatClient.prompt(message).call().content();
    }
}
