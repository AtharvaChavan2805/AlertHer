package com.womensafety.controller;

import com.womensafety.dto.ApiResponse;
import com.womensafety.service.RiskDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/risk")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RiskDetectionController {

    @Autowired
    private RiskDetectionService riskDetectionService;

    // Check risk level for a location
    @GetMapping("/check")
    public ResponseEntity<ApiResponse> checkRiskLevel(@RequestParam Double latitude, 
                                                      @RequestParam Double longitude) {
        try {
            String riskLevel = riskDetectionService.checkRiskLevel(latitude, longitude);
            String details = riskDetectionService.getRiskDetails(latitude, longitude);
            
            return ResponseEntity.ok(new ApiResponse(true, "Risk assessment completed", 
                    new RiskCheckResponse(riskLevel, details)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    // Get risk history for a user
    @GetMapping("/history/{userId}")
    public ResponseEntity<ApiResponse> getRiskHistory(@PathVariable Long userId) {
        try {
            var riskHistory = riskDetectionService.getRiskHistory(userId);
            return ResponseEntity.ok(new ApiResponse(true, "Risk history retrieved", riskHistory));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    // Get high-risk zones
    @GetMapping("/high-zones/{userId}")
    public ResponseEntity<ApiResponse> getHighRiskZones(@PathVariable Long userId) {
        try {
            var highRiskZones = riskDetectionService.getHighRiskZones(userId);
            return ResponseEntity.ok(new ApiResponse(true, "High-risk zones retrieved", highRiskZones));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    // Inner class for risk response
    public static class RiskCheckResponse {
        public String riskLevel;
        public String details;

        public RiskCheckResponse(String riskLevel, String details) {
            this.riskLevel = riskLevel;
            this.details = details;
        }
    }
}
