package com.example.rag;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.List;

@SpringBootTest(classes = com.example.rag.RagApplicationDemo.class)
@Import(VectorStoreTest.TestConfig.class)
public class VectorStoreTest {

    @TestConfiguration
    static class TestConfig{
        @Bean
        public SimpleVectorStore simpleVectorStore(EmbeddingModel embeddingModel){
            System.out.println("Creating SimpleVectorStore with embedding model: " + embeddingModel.getClass().getName());
            return SimpleVectorStore.builder(embeddingModel).build();
        }
    }

    @Autowired
    private SimpleVectorStore simpleVectorStore;

    @Autowired
    private ChatModel chatModel;
    @BeforeEach
    void setUp(){
        Document doc1 = Document.builder()
                .text("2025年夏季奥运会将于巴黎举行，预计吸引全球数百万观众")
                .build();
        Document doc2 = Document.builder()
                .text("对比学习框架下多语言BERT模型的语义表示分析")
                .build();
        Document doc3 = Document.builder()
                .text("暮色中的老槐树在风中摇曳，枯枝划破绯红的晚霞")
                .build();
        Document doc4 = Document.builder()
                .text("基于Transformer的预训练模型在机器翻译中的迁移学习研究")
                .build();
        List<Document> documents = List.of(doc1 , doc2 , doc3 , doc4);
        simpleVectorStore.add(documents);
        System.out.println("向量数据库存储成功");
    }

    @Test
    void save(){

    }

    @Test
    void search(){
//        List<Document> documents = simpleVectorStore.similaritySearch("机器学习");
//        System.out.println(documents);
        SearchRequest request = SearchRequest.builder()
                .query("机器学习")
                .topK(5)
                .similarityThreshold(0.45)
                .build();
        List<Document> documents = simpleVectorStore.similaritySearch(request);
        documents.forEach(System.out::println);
    }

    @Test
    void testRag(){
        ChatClient chatClient = ChatClient.builder(chatModel).build();
        QuestionAnswerAdvisor advisor = QuestionAnswerAdvisor.builder(simpleVectorStore).build();
        String message = "奥运会什么时候举行";
        String content = chatClient
                .prompt()
                .advisors(advisor)
                .user(message)
                .call()
                .content();
        System.out.println(content);
    }
}