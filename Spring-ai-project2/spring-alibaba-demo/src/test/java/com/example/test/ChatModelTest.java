package com.example.test;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageModel;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageOptions;
import com.example.demo.AlibabaApplicationDemo;
import org.junit.jupiter.api.Test;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = AlibabaApplicationDemo.class)
public class ChatModelTest {
    @Autowired
    public DashScopeChatModel dashScopeChatModel;

    @Test
    void testChat(){
        String content = dashScopeChatModel.call("你好，介绍一下Spring-ai");
        System.out.println(content);
    }

    @Autowired
    DashScopeImageModel imageModel;

    @Test
    void text2Img(){
        ImageResponse imageResponse = imageModel.call(new ImagePrompt("孩子在海边玩耍"));
        String imageUrl = imageResponse.getResult().getOutput().getUrl();
        System.out.println(imageUrl);
    }

    @Test
    void text2Img2(){
        DashScopeImageOptions options = DashScopeImageOptions.builder()
                .withModel("qwen-image-plus")
                .build();
        ImageResponse imageResponse = imageModel.call(new ImagePrompt("孩子在海边玩耍",options));
        String imgUrl = imageResponse.getResult().getOutput().getUrl();
        System.out.println(imgUrl);
    }

    @Test
    void textImg3(){
        String prompt = "一只猫咪在家中玩耍";
        DashScopeImageOptions options = DashScopeImageOptions.builder()
                .withModel("wan2.2-t2i-flash")
                .build();
        ImageResponse imageResponse = imageModel.call(new ImagePrompt(prompt,options));
        String imgUrl = imageResponse.getResult().getOutput().getUrl();
        System.out.println(imgUrl);
    }


}
