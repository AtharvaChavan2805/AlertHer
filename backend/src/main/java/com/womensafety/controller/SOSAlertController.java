package com.womensafety.controller;

import com.womensafety.dto.ApiResponse;
import com.womensafety.model.SOSAlert;
import com.womensafety.service.SOSAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/sos")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SOSAlertController {

    @Autowired
    private SOSAlertService sosAlertService;

    // Trigger SOS alert
    @PostMapping("/trigger")
    public ResponseEntity<ApiResponse> triggerSOS(@RequestParam Long userId, 
                                                  @RequestParam Double latitude, 
                                                  @RequestParam Double longitude) {
        try {
            SOSAlert sosAlert = sosAlertService.triggerSOS(userId, latitude, longitude);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "SOS Alert triggered successfully", sosAlert));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    // Get all SOS alerts for a user
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getSOSAlertsByUserId(@PathVariable Long userId) {
        try {
            List<SOSAlert> alerts = sosAlertService.getSOSAlertsByUserId(userId);
            return ResponseEntity.ok(new ApiResponse(true, "SOS Alerts retrieved", alerts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    // Get all active SOS alerts
    @GetMapping("/active/all")
    public ResponseEntity<ApiResponse> getActiveSOSAlerts() {
        try {
            List<SOSAlert> alerts = sosAlertService.getActiveSOSAlerts();
            return ResponseEntity.ok(new ApiResponse(true, "Active SOS Alerts retrieved", alerts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    // Update SOS alert status
    @PutMapping("/{alertId}/status")
    public ResponseEntity<ApiResponse> updateSOSAlertStatus(@PathVariable Long alertId, 
                                                           @RequestParam String status) {
        try {
            SOSAlert updatedAlert = sosAlertService.updateSOSAlertStatus(alertId, status);
            return ResponseEntity.ok(new ApiResponse(true, "SOS Alert status updated", updatedAlert));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    // Cancel SOS alert
    @PutMapping("/{alertId}/cancel")
    public ResponseEntity<ApiResponse> cancelSOSAlert(@PathVariable Long alertId) {
        try {
            SOSAlert cancelledAlert = sosAlertService.cancelSOSAlert(alertId);
            return ResponseEntity.ok(new ApiResponse(true, "SOS Alert cancelled", cancelledAlert));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    // Resolve SOS alert
    @PutMapping("/{alertId}/resolve")
    public ResponseEntity<ApiResponse> resolveSOSAlert(@PathVariable Long alertId) {
        try {
            SOSAlert resolvedAlert = sosAlertService.resolveSOSAlert(alertId);
            return ResponseEntity.ok(new ApiResponse(true, "SOS Alert resolved", resolvedAlert));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
}
