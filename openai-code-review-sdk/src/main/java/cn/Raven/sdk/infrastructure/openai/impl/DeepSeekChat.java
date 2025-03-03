package cn.Raven.sdk.infrastructure.deepseek.impl;

import cn.Raven.sdk.infrastructure.openai.IOpenAI;
import cn.Raven.sdk.infrastructure.openai.dto.ChatCompletionSyncResponseDTO;
import cn.Raven.sdk.infrastructure.openai.dto.ChatCompletionRequestDTO;
import cn.Raven.sdk.types.utils.BearerTokenUtils;
import com.alibaba.fastjson2.JSON;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DeepSeekChat implements IOpenAI {

    private  String apiHost="https://api.deepseek.com";
    private  String apiKeySecret="sk-e500c84c35604ef8bd24b079899610b6";

    public DeepSeekChat(String apiHost, String apiKeySecret) {
        this.apiHost = apiHost;
        this.apiKeySecret = apiKeySecret;
    }

    @Override
    public ChatCompletionSyncResponseDTO completions(ChatCompletionRequestDTO requestDTO) throws Exception {
        // 获取 Bearer Token
        String token = apiKeySecret;

        // 这里使用 DeepSeek API 的 URL
        URL url = new URL(apiHost + "/v1/chat/completions"); // 假设 API 地址是这样的
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + token);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // 将请求体对象转换成 JSON 字符串
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = JSON.toJSONString(requestDTO).getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // 获取返回数据
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        connection.disconnect();

        // 解析响应的 JSON 数据
        return JSON.parseObject(content.toString(), ChatCompletionSyncResponseDTO.class);
    }
}
