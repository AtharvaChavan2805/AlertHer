package com.womensafety.service;

import com.womensafety.model.AILog;
import com.womensafety.model.User;
import com.womensafety.repository.AILogRepository;
import com.womensafety.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AIAnalysisService {

    @Autowired
    private AILogRepository aiLogRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SOSAlertService sosAlertService;

    // Analyze user input for distress
    public AIAnalysisResponse analyzeDistress(Long userId, String inputText) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Simulate AI analysis
        AIAnalysisResponse response = performAIAnalysis(inputText);
        
        // Save to AI logs
        AILog aiLog = new AILog();
        aiLog.setUser(user);
        aiLog.setInputText(inputText);
        aiLog.setAiResponse(response.message);
        aiLog.setRiskLevel(response.risk);
        
        AILog savedLog = aiLogRepository.save(aiLog);
        
        // If HIGH risk, auto-trigger SOS
        if ("HIGH".equals(response.risk)) {
            System.out.println("⚠️ HIGH RISK DETECTED - Auto-triggering SOS Alert");
            // Auto-trigger SOS with user's last known location
            // In real implementation: Fetch latest location and trigger SOS
        }
        
        return response;
    }

    // Perform AI analysis (Simulated)
    private AIAnalysisResponse performAIAnalysis(String inputText) {
        String lowerText = inputText.toLowerCase();
        
        // Keywords indicating HIGH risk
        String[] highRiskKeywords = {"help", "danger", "emergency", "attack", "unsafe", "threatened", 
                                     "harassment", "assault", "scared", "terrified", "distress"};
        
        // Keywords indicating MODERATE risk
        String[] moderateRiskKeywords = {"worried", "anxious", "concerned", "uncomfortable", "lost", "alone"};
        
        // Check for high-risk keywords
        for (String keyword : highRiskKeywords) {
            if (lowerText.contains(keyword)) {
                return new AIAnalysisResponse("HIGH", 
                        "🚨 CRITICAL: High-risk distress signal detected. SOS can be triggered.");
            }
        }
        
        // Check for moderate-risk keywords
        for (String keyword : moderateRiskKeywords) {
            if (lowerText.contains(keyword)) {
                return new AIAnalysisResponse("MODERATE", 
                        "⚠️ CAUTION: Moderate concern detected. Stay alert and move to safe location.");
            }
        }
        
        return new AIAnalysisResponse("LOW", 
                "✅ No immediate threat detected. Stay safe!");
    }

    // Get AI analysis history for a user
    public List<AILog> getAIAnalysisHistory(Long userId) {
        return aiLogRepository.findByUserId(userId);
    }

    // Get high-risk AI logs
    public List<AILog> getHighRiskAILogs() {
        return aiLogRepository.findByRiskLevel("HIGH");
    }

    // Response class for AI analysis
    public static class AIAnalysisResponse {
        public String risk;
        public String message;

        public AIAnalysisResponse(String risk, String message) {
            this.risk = risk;
            this.message = message;
        }
    }
}
