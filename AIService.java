package com.college.student_chatbot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Service
public class AIService {
    
    @Value("${ai.api.key:}")
    private String apiKey;
    
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    
    public AIService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://generativelanguage.googleapis.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        this.objectMapper = new ObjectMapper();
    }
    
    public String getAIResponse(String userMessage) {
        if (apiKey == null || apiKey.isEmpty() || apiKey.equals("YOUR_AI_API_KEY_HERE")) {
            return getFallbackResponse(userMessage);
        }
        
        try {
            String requestBody = createRequestBody(userMessage);
            
            String response = webClient.post()
                    .uri("/v1beta/models/gemini-pro:generateContent?key=" + apiKey)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            
            return extractContentFromResponse(response);
            
        } catch (Exception e) {
            System.err.println("AI API Error: " + e.getMessage());
            return getFallbackResponse(userMessage);
        }
    }
    
    private String createRequestBody(String userMessage) {
        String systemPrompt = "You are a helpful student assistant for ABC College. Provide accurate, educational information about college-related queries. Be friendly and concise.";
            
        return String.format("""
            {
                "contents": [
                    {
                        "parts": [
                            {"text": "%s%s"}
                        ]
                    }
                ]
            }
            """, systemPrompt, "User Question: " + userMessage)
            .replace("\n", " ")
            .replace("\"", "\\\"");
    }
    
    private String extractContentFromResponse(String response) {
        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode candidates = root.path("candidates");
            
            if (candidates.isArray() && candidates.size() > 0) {
                JsonNode content = candidates.get(0).path("content");
                JsonNode parts = content.path("parts");
                
                if (parts.isArray() && parts.size() > 0) {
                    String aiResponse = parts.get(0).path("text").asText();
                    
                    if (aiResponse != null && !aiResponse.trim().isEmpty()) {
                        return aiResponse.trim();
                    }
                }
            }
            
            return getFallbackResponse("technical error");
            
        } catch (Exception e) {
            System.err.println("Response parsing error: " + e.getMessage());
            return getFallbackResponse("parsing error");
        }
    }
    
    private String getFallbackResponse(String userMessage) {
        String lowerMessage = userMessage.toLowerCase();
        
        if (lowerMessage.contains("research") || lowerMessage.contains("project")) {
            return "I currently don't have specific research opportunity details. Please contact our R&D department.";
        }
        else if (lowerMessage.contains("scholarship") || lowerMessage.contains("financial")) {
            return "For scholarship information, please visit the scholarship cell.";
        }
        else if (lowerMessage.contains("faculty") || lowerMessage.contains("professor")) {
            return "Our college has highly qualified faculty. For specific details, check our college website.";
        }
        else {
            return "I apologize, but I'm currently unable to access my advanced knowledge base. For this query, please contact administration at 022-12345678. Meanwhile, I can help with: courses, fees, admission, placement, library, and hostel information.";
        }
    }
}