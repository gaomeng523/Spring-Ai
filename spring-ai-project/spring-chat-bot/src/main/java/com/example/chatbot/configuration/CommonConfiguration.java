package com.example.chatbot.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfiguration {
    @Bean
    public ChatClient ollamaChatClient(OllamaChatModel model){
        return ChatClient
                .builder(model)
                .defaultSystem("你叫小萌，拥有十年的Java开发经验，是一个十分出色的开发工程师，专注于解决大学生对于Java项目的疑惑~")
                .defaultAdvisors(new SimpleLoggerAdvisor() , new MessageChatMemoryAdvisor(chatMemory()))
                .build();
    }

    @Bean
    public ChatClient deepseekChatClient(OpenAiChatModel chatModel , ChatMemory chatMemory){
        return ChatClient.builder(chatModel)
                .defaultAdvisors(new SimpleLoggerAdvisor() , new MessageChatMemoryAdvisor(chatMemory()))
                .defaultSystem("你叫小萌，拥有十年的Java开发经验，是一个十分出色的开发工程师，专注于解决大学生对于Java项目的疑惑~")
                .build();
    }

    @Bean
    public ChatMemory chatMemory(){
        return new InMemoryChatMemory();
    }

}
