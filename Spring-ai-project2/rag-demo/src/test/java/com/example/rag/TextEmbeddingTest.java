package com.example.rag;

import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class TextEmbeddingTest {

    @Autowired
    private DashScopeEmbeddingModel embeddingModel;

    //文本向量化
    @Test
    void textEmbed(){
        float[] embed  = embeddingModel.embed("高萌");
        System.out.println(embed.length);
        System.out.println(Arrays.toString(embed));
    }

    @Test
    void batchTextEmbed(){
        List<String> list = List.of("高萌" , "高萌","高萌" , "高萌","高萌" , "高萌","高萌" , "高萌","高萌" , "高萌");
        List<float[]> embed  = embeddingModel.embed(list);
        System.out.println(embed.size());
    }
}
