package com.womensafety.controller;

import com.womensafety.dto.ApiResponse;
import com.womensafety.model.Incident;
import com.womensafety.service.IncidentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/incident")
@CrossOrigin(origins = "*", maxAge = 3600)
public class IncidentController {

    @Autowired
    private IncidentService incidentService;

    // Report incident
    @PostMapping
    public ResponseEntity<ApiResponse> reportIncident(@RequestParam Long userId, 
                                                      @Valid @RequestBody Incident incident) {
        try {
            Incident savedIncident = incidentService.reportIncident(userId, incident);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Incident reported successfully", savedIncident));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    // Get all incidents for a user
    @GetMapping
    public ResponseEntity<ApiResponse> getIncidentsByUserId(@RequestParam Long userId) {
        try {
            List<Incident> incidents = incidentService.getIncidentsByUserId(userId);
            return ResponseEntity.ok(new ApiResponse(true, "Incidents retrieved", incidents));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    // Get incident by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getIncidentById(@PathVariable Long id) {
        try {
            Incident incident = incidentService.getIncidentById(id)
                    .orElseThrow(() -> new RuntimeException("Incident not found"));
            return ResponseEntity.ok(new ApiResponse(true, "Incident found", incident));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    // Get all incidents
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllIncidents() {
        try {
            List<Incident> incidents = incidentService.getAllIncidents();
            return ResponseEntity.ok(new ApiResponse(true, "All incidents retrieved", incidents));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    // Delete incident
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteIncident(@PathVariable Long id) {
        try {
            incidentService.deleteIncident(id);
            return ResponseEntity.ok(new ApiResponse(true, "Incident deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
}
