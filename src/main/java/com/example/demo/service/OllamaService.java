package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.util.*;

@Service
public class OllamaService {

    private final WebClient webClient;
    private final String activeModel = "phi3:latest";
    private final Map<String, List<Map<String, String>>> conversationHistory = new HashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OllamaService(@Value("${ollama.api.url:http://localhost:11434/api/chat}") String url) {
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .defaultHeader("Content-Type", "application/json")
                .build();
        
        System.out.println("==========================================");
        System.out.println("✅ Ollama متصل بنجاح!");
        System.out.println("📦 الموديل النشط: " + activeModel);
        System.out.println("==========================================");
    }

    public String chat(String userMessage, String sessionId) {
        try {
            // جلب تاريخ المحادثة
            List<Map<String, String>> messages = conversationHistory.computeIfAbsent(
                sessionId, k -> new ArrayList<>()
            );
            
            // إضافة رسالة المستخدم
            Map<String, String> userMsg = new HashMap<>();
            userMsg.put("role", "user");
            userMsg.put("content", userMessage);
            messages.add(userMsg);
            
            // تحضير الطلب
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", activeModel);
            requestBody.put("messages", messages);
            requestBody.put("stream", false);
            
            // إعدادات لتسريع الاستجابة
            Map<String, Object> options = new HashMap<>();
            options.put("num_predict", 512); // تقليل طول الإجابة
            options.put("temperature", 0.7);
            requestBody.put("options", options);

            System.out.println("🔄 جاري معالجة: \"" + userMessage + "\"...");

            // زيادة timeout إلى 3 دقائق (180 ثانية)
            String responseJson = webClient.post()
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofSeconds(180)) // زيادة timeout
                    .block();

            // تحليل الرد
            JsonNode rootNode = objectMapper.readTree(responseJson);
            
            if (rootNode.has("message")) {
                JsonNode messageNode = rootNode.get("message");
                if (messageNode.has("content")) {
                    String botReply = messageNode.get("content").asText();
                    
                    // حفظ رد البوت
                    Map<String, String> botMsg = new HashMap<>();
                    botMsg.put("role", "assistant");
                    botMsg.put("content", botReply);
                    messages.add(botMsg);
                    
                    System.out.println("✅ تم الاستلام");
                    return botReply;
                }
            }

            return "عذراً، لم أتمكن من الحصول على رد.";

        } catch (Exception e) {
            System.err.println("❌ خطأ: " + e.getMessage());
            
            // رسالة خطأ مفهومة للمستخدم
            if (e.getMessage().contains("Timeout")) {
                return "⚠️ السؤال معقد ويحتاج وقت أطول. حاول سؤال أبسط أو انتظر قليلاً وأعد المحاولة.";
            }
            
            return "⚠️ خطأ في الاتصال بـ Ollama. تأكد من تشغيل: ollama serve";
        }
    }

    public boolean isOllamaRunning() {
        try {
            String response = webClient.get()
                    .uri("http://localhost:11434/api/tags")
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofSeconds(5))
                    .block();
            return response != null;
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getActiveModel() {
        return activeModel;
    }
}