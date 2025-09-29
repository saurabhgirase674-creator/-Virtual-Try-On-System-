-- Database Schema for College Chatbot
-- This schema can be implemented when adding database persistence

-- Table to store chat sessions and conversations
CREATE TABLE chat_sessions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_id VARCHAR(100) NOT NULL,
    user_message TEXT NOT NULL,
    bot_response TEXT NOT NULL,
    message_type ENUM('HARDCODED', 'AI_GENERATED') NOT NULL,
    response_time_ms INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_session_id (session_id),
    INDEX idx_created_at (created_at)
);

-- Table for frequently asked questions and their answers
CREATE TABLE college_faq (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    question VARCHAR(500) NOT NULL,
    answer TEXT NOT NULL,
    category ENUM('COURSES', 'FEES', 'ADMISSION', 'PLACEMENT', 'HOSTEL', 'LIBRARY', 'GENERAL') NOT NULL,
    keywords TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_category (category),
    INDEX idx_keywords (keywords(255)),
    FULLTEXT idx_question (question)
);

-- Table for college course information
CREATE TABLE courses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_name VARCHAR(200) NOT NULL,
    duration_years INT NOT NULL,
    fees_per_year DECIMAL(10,2) NOT NULL,
    eligibility_criteria TEXT,
    syllabus_link VARCHAR(500),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table for user feedback
CREATE TABLE user_feedback (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_id VARCHAR(100),
    rating INT CHECK (rating >= 1 AND rating <= 5),
    feedback_text TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_session_id (session_id)
);

-- Insert sample data for courses
INSERT INTO courses (course_name, duration_years, fees_per_year, eligibility_criteria) VALUES
('B.Tech Computer Science', 4, 85000.00, '10+2 with Physics, Chemistry, Maths and 50% marks'),
('MCA', 2, 75000.00, 'Graduation with 50% marks and Mathematics in 12th'),
('MBA', 2, 65000.00, 'Graduation with 50% marks'),
('BBA', 3, 45000.00, '10+2 with 50% marks'),
('B.Com', 3, 40000.00, '10+2 with 50% marks');

-- Insert sample FAQ data
INSERT INTO college_faq (question, answer, category, keywords) VALUES
('What courses do you offer?', 'We offer: B.Tech (CS, IT, Mechanical, Civil), MCA, MBA, BBA, B.Com, B.Sc (Computer Science)', 'COURSES', 'courses,programs,branches,offer'),
('What is the fee structure?', 'Annual Fees: B.Tech - ₹85,000, MBA - ₹65,000, MCA - ₹75,000, BBA - ₹45,000, B.Com - ₹40,000', 'FEES', 'fee,fees,payment,cost,structure'),
('Tell me about placements', 'Placements 2024: 88% placed, Highest: ₹22 LPA, Average: ₹6.5 LPA, Top Companies: TCS, Infosys, Amazon, Microsoft', 'PLACEMENT', 'placement,job,company,recruitment,career');