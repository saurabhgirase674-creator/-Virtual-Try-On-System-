package com.college.student_chatbot;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class ChatbotService {
    
    private final AIService aiService;
    private Map<String, String> responses;
    private Random random;
    
    public ChatbotService(AIService aiService) {
        this.aiService = aiService;
        this.responses = new HashMap<>();
        this.random = new Random();
        initializeResponses();
    }
    
    private void initializeResponses() {
        responses.put("hello", "Hello! Welcome to ABC College Chatbot. How can I assist you today?");
        responses.put("hi", "Hi there! I'm your AI student assistant. What would you like to know?");
        responses.put("courses", "We offer: B.Tech (CS, IT, Mechanical, Civil), MCA, MBA, BBA, B.Com, B.Sc (Computer Science)");
        responses.put("mca", "MCA (Master of Computer Applications): 2-year program, Fees: ₹75,000 per year, Eligibility: Graduation with 50%");
        responses.put("computer science", "B.Tech Computer Science: 4-year program, Fees: ₹85,000/year, Specializations: AI, ML, Cyber Security");
        responses.put("fee", "Annual Fees: B.Tech - ₹85,000, MBA - ₹65,000, MCA - ₹75,000, BBA - ₹45,000, B.Com - ₹40,000");
        responses.put("mca fee", "MCA Fees: ₹75,000 per year. Scholarships available for meritorious students.");
        responses.put("btech fee", "B.Tech Fees: ₹85,000 per year. Includes tuition and examination fees.");
        responses.put("hostel", "Hostel Fees: AC Room - ₹50,000/year, Non-AC - ₹40,000/year. Includes food and amenities.");
        responses.put("library", "Library: 9 AM - 8 PM (Mon-Sat), Digital Library 24/7, 50,000+ books, 100+ journals");
        responses.put("placement", "Placements 2024: 88% placed, Highest: ₹22 LPA, Average: ₹6.5 LPA, Top Companies: TCS, Infosys, Amazon, Microsoft");
        responses.put("admission", "Admission: Online application, Documents: 10th/12th marksheet, TC, Caste certificate, Migration certificate");
        responses.put("contact", "Contact: Phone - 022-12345678, Email - info@abccollege.edu, Address: College Road, Mumbai");
        responses.put("timing", "College Hours: 9 AM - 4 PM, Office: 10 AM - 5 PM (Mon-Fri)");
    }
    
    public String getResponse(String userMessage) {
        if (userMessage == null || userMessage.trim().isEmpty()) {
            return "Please type your question. I'm here to help!";
        }
        
        String lowerMessage = userMessage.toLowerCase().trim();
        String hardcodedResponse = getHardcodedResponse(lowerMessage);
        if (hardcodedResponse != null) {
            return hardcodedResponse;
        }
        
        return aiService.getAIResponse(userMessage);
    }
    
    private String getHardcodedResponse(String message) {
        for (String key : responses.keySet()) {
            if (message.equals(key)) {
                return responses.get(key);
            }
        }
        
        if (message.contains("mca") && message.contains("fee")) {
            return responses.get("mca fee");
        } else if (message.contains("btech") && message.contains("fee")) {
            return responses.get("btech fee");
        } else if (message.contains("mca")) {
            return responses.get("mca");
        } else if (message.contains("course") || message.contains("program") || message.contains("branch")) {
            return responses.get("courses");
        } else if (message.contains("fee") || message.contains("payment") || message.contains("cost")) {
            return responses.get("fee");
        } else if (message.contains("library")) {
            return responses.get("library");
        } else if (message.contains("placement") || message.contains("job") || message.contains("company")) {
            return responses.get("placement");
        } else if (message.contains("hostel") || message.contains("accommodation")) {
            return responses.get("hostel");
        } else if (message.contains("admission") || message.contains("admit") || message.contains("apply")) {
            return responses.get("admission");
        } else if (message.contains("contact") || message.contains("email") || message.contains("phone")) {
            return responses.get("contact");
        } else if (message.contains("time") || message.contains("timing") || message.contains("hour")) {
            return responses.get("timing");
        } else if (message.contains("hello") || message.contains("hi") || message.contains("hey")) {
            return getGreetingResponse();
        } else if (message.contains("thank")) {
            return getThankYouResponse();
        } else if (message.contains("bye") || message.contains("goodbye")) {
            return "Thank you for chatting! Have a great day! 🎓";
        }
        
        return null;
    }
    
    private String getGreetingResponse() {
        String[] greetings = {
            "Hello! Welcome to ABC College Chatbot. How can I assist you today?",
            "Hi there! I'm your AI student assistant. What would you like to know about our college?",
            "Namaste! I'm here to help with college queries. How can I assist you?"
        };
        return greetings[random.nextInt(greetings.length)];
    }
    
    private String getThankYouResponse() {
        String[] thanks = {
            "You're welcome! Feel free to ask if you have more questions.",
            "Glad I could help! Let me know if you need anything else.",
            "Happy to assist! Don't hesitate to reach out for more information."
        };
        return thanks[random.nextInt(thanks.length)];
    }
}