package cn.Raven.sdk.infrastructure.deepseek.impl;

import cn.Raven.sdk.infrastructure.openai.IOpenAI;
import cn.Raven.sdk.infrastructure.openai.dto.ChatCompletionSyncResponseDTO;
import cn.Raven.sdk.infrastructure.openai.dto.ChatCompletionRequestDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson2.JSON;

import java.util.HashMap;
import java.util.Map;

public class DeepSeekChat implements IOpenAI {

    private String apiHost = "https://api.deepseek.com";
    private String apiKeySecret = "sk-e500c84c35604ef8bd24b079899610b6"; // 请注意，实际使用时要更换为你的真实API密钥

    public DeepSeekChat(String apiHost, String apiKeySecret) {
        this.apiHost = apiHost;
        this.apiKeySecret = apiKeySecret;
    }

    @Override
    public ChatCompletionSyncResponseDTO completions(ChatCompletionRequestDTO requestDTO) throws Exception {
        // 使用 RestTemplate 实例
        RestTemplate restTemplate = new RestTemplate();

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKeySecret);

        // 设置请求体，构建消息
        Map<String, Object> requestBody = new HashMap<>();
        Map<String, String> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", "你好，DeepSeek！");

        // 构造请求体，注意这里是将messages构造为一个对象数组
        requestBody.put("model", "deepseek-chat");
        requestBody.put("messages", new Object[]{message});
        requestBody.put("temperature", 0.7);

        // 创建 HttpEntity 以包含请求头和请求体
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // 发送 POST 请求
        ResponseEntity<String> response = restTemplate.postForEntity(
                apiHost + "/v1/chat/completions", // DeepSeek API URL
                requestEntity,
                String.class
        );

        // 处理响应
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("DeepSeek响应: " + response.getBody());
            return JSON.parseObject(response.getBody(), ChatCompletionSyncResponseDTO.class);
        } else {
            System.out.println("请求失败，状态码: " + response.getStatusCode());
            throw new Exception("API请求失败，返回状态码: " + response.getStatusCode());
        }
    }
}
