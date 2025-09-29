# API Endpoints

## POST /chat
Send user message and get bot response

**Request:**
```json
{"message": "What courses?"}
{"response": "We offer: B.Tech, MCA, MBA..."}

## **TERA EXISTING CODE FILES** (jo tune diye the):

### **src/main/java/com/college/student_chatbot/StudentChatbotApplication.java**
```java
package com.college.student_chatbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentChatbotApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentChatbotApplication.class, args);
    }
}