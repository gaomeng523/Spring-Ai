package com.bit.ai.controller;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/ds")
@RestController
public class DeepseekController {
    @Autowired
    private OpenAiChatModel openAiChatModel;
    @GetMapping("/chat")
    public String chat(String message){
        return openAiChatModel.call(message);
    }
}
