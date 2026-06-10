package com.example.test;

import com.alibaba.cloud.ai.dashscope.audio.DashScopeAudioTranscriptionModel;
import com.alibaba.cloud.ai.dashscope.audio.DashScopeAudioTranscriptionOptions;
import com.alibaba.dashscope.audio.asr.transcription.Transcription;
import com.alibaba.dashscope.audio.asr.transcription.TranscriptionParam;
import com.alibaba.dashscope.audio.asr.transcription.TranscriptionQueryParam;
import com.alibaba.dashscope.audio.asr.transcription.TranscriptionResult;
import com.example.demo.AlibabaApplicationDemo;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.util.Arrays;

@SpringBootTest(classes = AlibabaApplicationDemo.class)
public class AudioTranscriptionModelTest {

    private DashScopeAudioTranscriptionModel transcriptionModel;

    private final String DEFAULT_MODEL = "paraformer-v2";
    @Test
    void stt(){
        // 加载音频资源（这里是阿里云OSS上的示例音频）
        Resource resource = new DefaultResourceLoader()
                .getResource("https://dashscope.oss-cn-beijing.aliyuncs.com/samples/audio/paraformer/hello_world_female2.wav");

        // 调用语音转文字模型
        AudioTranscriptionResponse response = transcriptionModel.call(
                new AudioTranscriptionPrompt(
                        resource,
                        DashScopeAudioTranscriptionOptions.builder()
                                .withModel(DEFAULT_MODEL)
                                .build()
                )
        );

        // 打印识别结果
        System.out.println(response.getResult().getOutput());
    }

    @Test
    void sttByDashscopeSdk(){
        TranscriptionParam param =
                TranscriptionParam.builder()
                        // 新加坡和北京地域的API Key不同。获取API Key：https://help.aliyun.com/zh/model-studio/get-api-key
                        // 若没有配置环境变量，请用百炼API Key将下行替换为：.apiKey("sk-xxx")
                        //.apiKey("apikey")
                        .model("fun-asr") // 此处以fun-asr为例，可按需更换模型名称。模型列表：https://help.aliyun.com/zh/model-studio/models
                        .fileUrls(
                                Arrays.asList(
                                        "https://dashscope.oss-cn-beijing.aliyuncs.com/samples/audio/paraformer/hello_world_female2.wav"))
                        .build();
        try {
            Transcription transcription = new Transcription();
            // 提交转写请求
            TranscriptionResult result = transcription.asyncCall(param);
            System.out.println("RequestId: " + result.getRequestId());
            // 阻塞等待任务完成并获取结果
            result = transcription.wait(
                    TranscriptionQueryParam.FromTranscriptionParam(param, result.getTaskId()));
            // 打印结果
            System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(result.getOutput()));
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
        System.exit(0);
    }
}
