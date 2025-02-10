package cn.Raven.sdk.test;

import cn.Raven.sdk.domain.model.ChatCompletionSyncResponse;
import cn.Raven.sdk.types.utils.BearerTokenUtils;
import com.alibaba.fastjson2.JSON;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @Description
 * @Author Raven
 * @Date 2025/2/9
 */
public class ApiTest {
    public static void main(String[] args) {
        String apiKeySecret="87e2801ef8ab4b98bd8feba6266d1818.YlbPP9YlesyDXerS";
        String token = BearerTokenUtils.getToken(apiKeySecret);
        System.out.println(token);
    }

    @Test
    public void test_http() throws Exception {
        String apiKeySecret="87e2801ef8ab4b98bd8feba6266d1818.YlbPP9YlesyDXerS";
        String token = BearerTokenUtils.getToken(apiKeySecret);

        URL url=new URL("https://open.bigmodel.cn/api/paas/v4/chat/completions");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization","Bearer "+token);
        connection.setRequestProperty("Content-Type","application/json");
        connection.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        connection.setDoOutput(true);
        String code = "1+1";
        String jsonInpuString = "{"
                + "\"model\":\"glm-4-flash\","
                + "\"messages\": ["
                + "    {"
                + "        \"role\": \"user\","
                + "        \"content\": \"你是一个高级编程架构师，精通各类场景方案、架构设计和编程语言请，请您根据git diff记录，对代码做出评审。代码为: " + code + "\""
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

        ChatCompletionSyncResponse response = JSON.parseObject(content.toString(), ChatCompletionSyncResponse.class);
        System.out.println(response.getChoices().get(0).getMessage().getContent());

    }

}
