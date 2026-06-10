package com.example.demo.controller;

import com.example.demo.tool.DateTimeTools;
import com.example.demo.tool.WeatherTools;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.method.MethodToolCallback;
import org.springframework.ai.tool.support.ToolDefinitions;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;

@RequestMapping("/chatmodel")
@RestController
public class ChatModelController {

    private final ChatModel chatModel;
    public ChatModelController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/call")
    public String call(@RequestParam String message){
        Prompt prompt = new Prompt(message);
        return chatModel.call(prompt).getResult().getOutput().getText();
    }

    @GetMapping("/callByTool")
    public String callByTool(@RequestParam String message){
        ToolCallback[] dateTimeTools = ToolCallbacks.from(new DateTimeTools());
        ChatOptions chatOptions = ToolCallingChatOptions.builder()
                .toolCallbacks(dateTimeTools)
                .build();
        Prompt prompt = new Prompt(message, chatOptions);
        return chatModel.call(prompt).getResult().getOutput().getText();
    }

    @GetMapping("/callByTool2")
    public String callByTool2(@RequestParam String message){
        Method method = ReflectionUtils.findMethod(WeatherTools.class, "getCurrentWeatherByCityName" , String.class);
        ToolCallback toolCallback = MethodToolCallback.builder()
                .toolDefinition(ToolDefinitions
                        .builder(method)
                        .description("根据指定的城市名称，获取城市当前的天气")
                        .build())
                .toolMethod(method)
                .toolObject(new WeatherTools())
                .build();

        ChatOptions chatOptions = ToolCallingChatOptions.builder()
                .toolCallbacks(toolCallback)
                .build();
        Prompt prompt = new Prompt(message, chatOptions);
        return chatModel.call(prompt).getResult().getOutput().getText();
    }
}