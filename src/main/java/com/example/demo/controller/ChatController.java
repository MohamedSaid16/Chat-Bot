package com.example.demo.controller;

import com.example.demo.model.ChatRequest;
import com.example.demo.model.ChatResponse;
import com.example.demo.service.ChatService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    private final ChatService chatService;

    public ChatController() {
        this.chatService = new ChatService();
        System.out.println("✅ ChatController initialized");
    }

    @GetMapping
    public String test() {
        return "✅ Chat API is working. Use POST to /chat with JSON {\"message\":\"your message\"}";
    }

    @PostMapping
    public ChatResponse chat(@RequestBody ChatRequest request, HttpServletRequest httpRequest) {
        
        // إنشاء Session ID
        String sessionId = getOrCreateSessionId(httpRequest);
        
        // الحصول على الرد
        String reply = chatService.getReply(request.getMessage(), sessionId);
        
        return new ChatResponse(reply);
    }
    
    private String getOrCreateSessionId(HttpServletRequest request) {
        String sessionId = request.getHeader("X-Session-ID");
        if (sessionId == null || sessionId.isEmpty()) {
            sessionId = UUID.randomUUID().toString();
        }
        return sessionId;
    }
}