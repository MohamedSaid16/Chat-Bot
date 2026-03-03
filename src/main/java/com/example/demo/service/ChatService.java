package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final OllamaService ollamaService;
    private final boolean useOllama;

    public ChatService() {
        this.ollamaService = new OllamaService("http://localhost:11434/api/chat");
        this.useOllama = ollamaService.isOllamaRunning();
        
        if (useOllama) {
            System.out.println("==========================================");
            System.out.println("✅ Ollama متصل!");
            System.out.println("📦 الموديل: " + ollamaService.getActiveModel());
            System.out.println("==========================================");
        } else {
            System.out.println("⚠️ Ollama غير متصل. شغل: ollama serve");
        }
    }

    public String getReply(String message, String sessionId) {
        if (message == null || message.trim().isEmpty()) {
            return "الرجاء كتابة رسالة 😊";
        }

        if (useOllama) {
            try {
                return ollamaService.chat(message, sessionId);
            } catch (Exception e) {
                return "⚠️ حدث خطأ: " + e.getMessage();
            }
        } else {
            return "⚠️ Ollama غير متصل. الرجاء تشغيل: ollama serve";
        }
    }
}