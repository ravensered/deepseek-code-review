package cn.Raven.sdk.test;

import cn.Raven.sdk.domain.model.ChatCompletionSyncResponse;
import cn.Raven.sdk.types.utils.BearerTokenUtils;
import cn.Raven.sdk.types.utils.WXAccessTokenUtils;
import com.alibaba.fastjson2.JSON;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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


    @Test
    public void test_wx(){
        String accessToken = WXAccessTokenUtils.getAccessToken();
        System.out.println("accessToken = " + accessToken);

        Message message = new Message();
        message.put("project","big-market");
        message.put("review","代码评审测试");


        String url = String.format("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s", accessToken);
        sendPostRequest(url, JSON.toJSONString(message));

    }
    private static void sendPostRequest(String urlString, String jsonBody) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            try (Scanner scanner = new Scanner(conn.getInputStream(), StandardCharsets.UTF_8.name())) {
                String response = scanner.useDelimiter("\\A").next();
                System.out.println(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static class Message{
        private String touser ="oxF5Q7HPRA3y_jj68Nfszq7siPA0";
        private String template_id="nyPLEITbmzbPGs60Oh1F1mZ9OFmLv7w3mrMc7KvlAc0";
        private String url="https://github.com/ravensered/openai-code-review-log/blob/main/2025-02-11/IacJBgYLgWdu.md";
        private Map<String ,Map<String,String>> data = new HashMap<>();


        public void put(String key ,String value){
            data.put(key,new HashMap<String,String>(){
                {put("value",value);}
            });
        }
        public String getTouser() {
            return touser;
        }

        public void setTouser(String touser) {
            this.touser = touser;
        }

        public String getTemplate_id() {
            return template_id;
        }

        public void setTemplate_id(String template_id) {
            this.template_id = template_id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Map<String, Map<String, String>> getData() {
            return data;
        }

        public void setData(Map<String, Map<String, String>> data) {
            this.data = data;
        }
    }

}
