package com.bit.ai.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {
    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder){
//        在Spring AI 的ChatClient.Builer 中，defaultSystem()方法用于设置ai模型的默认系统消息，它会作为对话的基础角色设定或初始指令，
//        通过ChatClient.Builder链式调用设置后，该方法定义的文本会作为系统消息注入到每次对话的上下文中，用于引导AI的回复风格或身份设定。

        //Spring AI 内置了⼀些Advisor, SimpleLoggerAdvisor 作为其中之⼀, 主要功能是记录⽇志. 使⽤⾮常简单,
        // 开发⼈员只需把它添加到Advisor链中, 即可⾃动记录所有经过该Advisor的聊天请求和响应, 并且开发⼈员可以对其进⾏配置, ⽐如⽇志级别和⽇志格式.
        return chatClientBuilder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultSystem("你叫小萌，你是一个Java开发工程师，有三年的开发经验，精通Java的各种开发").build();
    }
}
