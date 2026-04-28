package com.womensafety.controller;

import com.womensafety.dto.ApiResponse;
import com.womensafety.service.AIAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ai")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AIAnalysisController {

    @Autowired
    private AIAnalysisService aiAnalysisService;

    // Analyze distress message
    @PostMapping("/analyze")
    public ResponseEntity<ApiResponse> analyzeDistress(@RequestParam Long userId, 
                                                       @RequestParam String message) {
        try {
            AIAnalysisService.AIAnalysisResponse response = aiAnalysisService.analyzeDistress(userId, message);
            
            Map<String, Object> result = new HashMap<>();
            result.put("riskLevel", response.risk);
            result.put("message", response.message);
            result.put("autoSOSTriggered", "HIGH".equals(response.risk));
            
            return ResponseEntity.ok(new ApiResponse(true, "AI analysis completed", result));
        } catch (Exception e) {
            return ResponseEntity.status(400)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    // Get AI analysis history
    @GetMapping("/history/{userId}")
    public ResponseEntity<ApiResponse> getAnalysisHistory(@PathVariable Long userId) {
        try {
            var history = aiAnalysisService.getAIAnalysisHistory(userId);
            return ResponseEntity.ok(new ApiResponse(true, "Analysis history retrieved", history));
        } catch (Exception e) {
            return ResponseEntity.status(404)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    // Get all high-risk AI logs
    @GetMapping("/high-risk")
    public ResponseEntity<ApiResponse> getHighRiskLogs() {
        try {
            var logs = aiAnalysisService.getHighRiskAILogs();
            return ResponseEntity.ok(new ApiResponse(true, "High-risk logs retrieved", logs));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
}
