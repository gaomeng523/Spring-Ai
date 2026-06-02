package com.example.chatbot.controller;

import com.example.chatbot.entity.ChatInfo;
import com.example.chatbot.entity.MessageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import com.example.chatbot.repository.MemoryChatHistoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("/chat")
@RestController
public class ChatController {
    private final ChatClient chatClient;

//    public ChatController(ChatClient ollamaChatClient) {
//        this.chatClient = ollamaChatClient;
//    }


    public ChatController(ChatClient deepseekChatClient) {
        this.chatClient = deepseekChatClient;
    }

    @Autowired
    private MemoryChatHistoryRepository memoryChatHistoryRepository;
    @Autowired
    private ChatMemory chatMemory;

    @RequestMapping(value = "/stream" , produces = "text/html;charset=utf-8")
    public Flux<String> stream(String prompt , String chatId){
        log.info("prompt:{} , chatId:{}", prompt,chatId);
        //保存会话

        memoryChatHistoryRepository.save(prompt , chatId);
        return chatClient.prompt()
                .user(prompt)
                .advisors(spec -> spec.param(AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .stream()
                .content();
    }


    @RequestMapping("/getChatIds")
    public List<ChatInfo> getChatIds(){
        return memoryChatHistoryRepository.getChats();
    }

    /**
     * 获取会话记录
     * @param chatId
     * @return
     */
    @RequestMapping("/getChatHistory")
    public List<MessageVO> getChatHistory(String chatId){
        log.info("获取会话记录，chatId:{}" , chatId);
        List<Message> messages = chatMemory.get(chatId , 20);
        return messages.stream().map(MessageVO::new).collect(Collectors.toList());
    }

    /**
     * 删除会话
     * @param chatId
     * @return
     */
    @RequestMapping("/deleteChat")
    public Boolean deleteChat(String chatId){
        log.info("删除会话 ， chatId:{}" , chatId);
        try{
            memoryChatHistoryRepository.clearChatId(chatId);
            chatMemory.clear(chatId);
        }catch (Exception e){
            log.info("删除会话失败 ， chatId:{}" , chatId);
            return false;
        }
        return true;
    }
}
