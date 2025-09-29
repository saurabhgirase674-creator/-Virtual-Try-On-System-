package com.college.student_chatbot;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ChatbotController {
    
    private final ChatbotService chatbotService;
    
    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Welcome to College Chatbot!");
        return "chatbot";
    }
    
    @PostMapping("/chat")
    @ResponseBody
    public ChatResponse chat(@RequestBody ChatRequest request) {
        String userMessage = request.getMessage();
        String botResponse = chatbotService.getResponse(userMessage);
        return new ChatResponse(botResponse);
    }
    
    @GetMapping("/chat")
    @ResponseBody
    public ChatResponse chatGet(@RequestParam String message) {
        String botResponse = chatbotService.getResponse(message);
        return new ChatResponse(botResponse);
    }
    
    public static class ChatRequest {
        private String message;
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
    
    public static class ChatResponse {
        private String response;
        public ChatResponse(String response) { this.response = response; }
        public String getResponse() { return response; }
        public void setResponse(String response) { this.response = response; }
    }
}