package com.bit.ollama.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfiguation {
    @Bean
    public ChatClient chatClient(OllamaChatModel model){
        return ChatClient.builder(model).defaultSystem("你叫小萌，拥有十年的Java开发经验，是一个十分出色的开发工程师，专注于解决大学生对于Java项目的疑惑").build();
    }

}
