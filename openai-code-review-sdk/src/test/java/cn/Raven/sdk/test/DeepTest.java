package cn.Raven.sdk.test;

import cn.Raven.sdk.domain.service.AbstractOpenAiCodeReviewService;
import cn.Raven.sdk.domain.service.impl.OpenAiCodeReviewService;
import cn.Raven.sdk.infrastructure.openai.IOpenAI;
import cn.Raven.sdk.infrastructure.openai.dto.ChatCompletionRequestDTO;
import cn.Raven.sdk.infrastructure.openai.dto.ChatCompletionSyncResponseDTO;
import cn.Raven.sdk.infrastructure.openai.impl.DeepSeekChat;
import cn.Raven.sdk.types.utils.BearerTokenUtils;
import com.alibaba.fastjson2.JSON;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * @Description
 * @Author Raven
 * @Date 2025/3/15
 */
public class DeepTest {


    @Test
    public void send() throws IOException {
        String apiKeySecret="sk-e500c84c35604ef8bd24b079899610b6";


        URL url=new URL("https://api.deepseek.com/chat/completions");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + apiKeySecret);
        connection.setDoOutput(true);
        String code = "1+1";
        String jsonInpuString = "{"
                + "\"model\": \"deepseek-chat\","
                + "\"messages\": ["
                + "    {"
                + "        \"content\": \"You are a helpful assistant\","
                + "        \"role\": \"system\""
                + "    },"
                + "    {"
                + "        \"content\": \"你是一个高级编程架构师，精通各类场景方案、架构设计和编程语言请，请您根据git diff记录，对代码做出评审。代码为: " + code + "\","
                + "        \"role\": \"user\""
                + "    }"
                + "]"
                + "}";



        try(OutputStream os = connection.getOutputStream()){
            byte[] input = jsonInpuString.getBytes(StandardCharsets.UTF_8);
            os.write(input);
        }

        int responseCode = connection.getResponseCode();
        System.out.println(responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;

        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null){
            content.append(inputLine);
        }

        in.close();
        connection.disconnect();

        ChatCompletionSyncResponseDTO response = JSON.parseObject(content.toString(), ChatCompletionSyncResponseDTO.class);
        System.out.println(response.getChoices().get(0).getMessage().getContent());
    }

    @Test
    public void test() throws Exception {
        DeepTest deepTest=new DeepTest();
        String s = deepTest.codeReview("1+1");
        System.out.println(s);
    }



    public String codeReview(String diffCode) throws Exception {
        IOpenAI openAI=new DeepSeekChat("https://api.deepseek.com/chat/completions","sk-e500c84c35604ef8bd24b079899610b6");
        ChatCompletionRequestDTO chatCompletionRequest = new ChatCompletionRequestDTO();

        chatCompletionRequest.setMessages(new ArrayList<ChatCompletionRequestDTO.Prompt>() {

            {
                add(new ChatCompletionRequestDTO.Prompt("user", "你是一个高级编程架构师，精通各类场景方案、架构设计和编程语言请，请您根据git diff记录，对代码做出评审。代码如下:"));
                add(new ChatCompletionRequestDTO.Prompt("user", diffCode));
            }
        });

        ChatCompletionSyncResponseDTO completions = openAI.completions(chatCompletionRequest);
        ChatCompletionSyncResponseDTO.Message message = completions.getChoices().get(0).getMessage();
        return message.getContent();
    }
}
