package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {
    DataSourceAutoConfiguration.class,
    SecurityAutoConfiguration.class
})
public class ChatbotBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatbotBackendApplication.class, args);
        System.out.println("\n==========================================");
        System.out.println("🚀 شات بوت G11 شغال على port 8081");
        System.out.println("📝 استخدم: POST http://localhost:8081/chat");
        System.out.println("📦 مع Ollama: " + (checkOllama() ? "✅ متصل" : "⚠️ غير متصل"));
        System.out.println("==========================================\n");
    }
    
    private static boolean checkOllama() {
        try {
            java.net.Socket socket = new java.net.Socket("localhost", 11434);
            socket.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}