package com.golemon.plugins.service.impl;

import com.golemon.blogbackend.common.vo.ResponseResult;
import com.golemon.blogbackend.enums.HttpStatusCodeEnum;
import com.golemon.plugins.service.AIChatService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class AIChatServiceImpl implements AIChatService {

    @Value("${deepseek.api.key}")
    private String apiKey;

    @Value("${deepseek.api.url}")
    private String apiUrl;

    private final WebClient webClient;

    public AIChatServiceImpl() {
        this.webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public ResponseResult<?> chat(String message) {
        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "deepseek-chat");
            String systemContent = "Do not use markdown syntax for the output content, and make it as concise as possible for easy reading.";
            message = systemContent + message;
            Object[] messages = new Object[]{
                    new HashMap<>(Map.of("role", "user", "content", message))
            };
            System.out.println("messages: " + Arrays.toString(messages));
            requestBody.put("messages", messages);
//            requestBody.put("messages", new Object[]{
//                Map.of("role", "user", "content", message)
//            });

            String response = webClient.post()
                    .uri(apiUrl)
                    .header("Authorization", "Bearer " + apiKey)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .map(res -> {
                        if (res.containsKey("choices")) {
                            Map<String, Object> choice = (Map<String, Object>) ((java.util.List<?>) res.get("choices")).get(0);
                            Map<String, Object> messageResponse = (Map<String, Object>) choice.get("message");
                            return (String) messageResponse.get("content");
                        }
                        return "Sorry, I cannot understand your request.";
                    })
                    .block();

            return ResponseResult.okResult(response);
        } catch (Exception e) {
            return ResponseResult.errorResult(HttpStatusCodeEnum.valueOf("Sorry, the service is temporarily unavailable. Please try again later."));
        }
    }
} 