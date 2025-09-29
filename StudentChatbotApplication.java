package com.college.student_chatbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentChatbotApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentChatbotApplication.class, args);
        System.out.println("🚀 College Chatbot Started Successfully!");
        System.out.println("📱 Open: http://localhost:8081");
    }
}