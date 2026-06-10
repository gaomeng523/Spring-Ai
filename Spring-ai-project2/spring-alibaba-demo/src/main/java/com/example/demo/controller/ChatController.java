package com.example.demo.controller;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.example.demo.config.ToolConfig;
import com.example.demo.tool.DateTimeTools;
import com.example.demo.tool.WeatherTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.method.MethodToolCallback;
import org.springframework.ai.tool.support.ToolDefinitions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;

@RestController
@RequestMapping("/chat")
public class ChatController {

    // 注入ChatModel和工具类
    public ChatClient chatClient;

    public ChatController(DashScopeChatModel chatModel) {
        this.chatClient = ChatClient
                .builder(chatModel)
                .defaultTools(new DateTimeTools())
                .build();
    }

    @Autowired
    public ChatController(DashScopeChatModel chatModel , ToolCallback weatherTool) {
        this.chatClient = ChatClient
                .builder(chatModel)
                .defaultTools(new DateTimeTools())
                .defaultToolCallbacks(weatherTool)
                .build();
    }

    @RequestMapping("/call")
    public String call(String message) {
        return chatClient.prompt()
                .user(message)
    //           .tools(new DateTimeTools())
                .call()
                .content();
    }

    @RequestMapping("/call2")
    public String call2(String message) {
        Method method = ReflectionUtils.findMethod(WeatherTools.class, "getCurrentWeatherByCityName" , String.class);
        ToolCallback toolCallback = MethodToolCallback.builder()
                .toolDefinition(ToolDefinitions
                        .builder(method)
                        .description("根据指定的城市名称，获取城市当前的天气")
                        .build())
                .toolMethod(method)
                .toolObject(new WeatherTools())
                .build();

        return chatClient.prompt()
                .user(message)
                .tools(new DateTimeTools())
                .toolCallbacks(toolCallback)
                .call()
                .content();
    }
}