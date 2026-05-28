package com.bit.ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    public ChatClient chatClient;

    @GetMapping("/call")
    public String generation(String userInput){
        return this.chatClient.prompt()
                //用户输入的信息
                .user(userInput).advisors(new SimpleLoggerAdvisor())
                //请求的大模型
                .call()
                //返回文本
                .content();
    }

//    如果您想从 LLM 接收结构化输出, Spring AI⽀持将 ChatModel/ChatClient ⽅法的返回类型从
//    String 更改为其他类型.
//    通过 entity() ⽅法将模型输出转为⾃定义实体, 需确保输出格式符合JSON规范
    //创建一个实体类
    record Recipe(String dish, List<String> ingredients){}

    @GetMapping("/entity")
    public String entity(String userInput){
        Recipe recipe = this.chatClient.prompt()
                .user(String.format("请帮我生成%s的食谱" , userInput))
                .call()
                .entity(Recipe.class);
        return recipe.toString();
    }

    @GetMapping(value = "/stream", produces = "text/html;charset=utf-8")
    public Flux<String> stream(String userInput){
        return this.chatClient.prompt().user(userInput).stream().content();
    }
}
