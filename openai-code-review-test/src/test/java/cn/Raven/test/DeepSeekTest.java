package cn.Raven.test;

import cn.Raven.sdk.domain.model.Model;
import cn.Raven.sdk.infrastructure.deepseek.impl.DeepSeekChat;
import cn.Raven.sdk.infrastructure.openai.IOpenAI;
import cn.Raven.sdk.infrastructure.openai.dto.ChatCompletionRequestDTO;
import cn.Raven.sdk.infrastructure.openai.dto.ChatCompletionSyncResponseDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * @Description
 * @Author Raven
 * @Date 2025/3/3
 */

public class DeepSeekTest {
    IOpenAI iOpenAI=new DeepSeekChat("https://api.deepseek.com","sk-e500c84c35604ef8bd24b079899610b6");

    @Test
    public void test1() throws Exception {
        ChatCompletionRequestDTO chatCompletionRequest = new ChatCompletionRequestDTO();
        chatCompletionRequest.setModel(Model.GLM_4_FLASH.getCode());
        chatCompletionRequest.setMessages(new ArrayList<ChatCompletionRequestDTO.Prompt>() {

            {
                add(new ChatCompletionRequestDTO.Prompt("user", "你是一个高级编程架构师，精通各类场景方案、架构设计和编程语言请，请您根据git diff记录，对代码做出评审。代码如下:"));

            }
        });

        ChatCompletionSyncResponseDTO completions = iOpenAI.completions(chatCompletionRequest);
        ChatCompletionSyncResponseDTO.Message message = completions.getChoices().get(0).getMessage();
        System.out.println(message.getContent());
    }
}
