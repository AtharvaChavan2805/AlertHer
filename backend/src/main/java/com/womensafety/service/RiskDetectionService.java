package com.womensafety.service;

import com.womensafety.model.Incident;
import com.womensafety.model.RiskLog;
import com.womensafety.model.User;
import com.womensafety.repository.IncidentRepository;
import com.womensafety.repository.RiskLogRepository;
import com.womensafety.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RiskDetectionService {

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private RiskLogRepository riskLogRepository;

    @Autowired
    private UserRepository userRepository;

    private static final double DISTANCE_THRESHOLD = 0.05; // ~5km in degrees
    private static final int HIGH_RISK_INCIDENT_COUNT = 5;
    private static final int MODERATE_RISK_INCIDENT_COUNT = 2;

    // Check risk level for a location
    public String checkRiskLevel(Double latitude, Double longitude) {
        List<Incident> nearbyIncidents = incidentRepository.findIncidentsNear(latitude, longitude, DISTANCE_THRESHOLD);
        
        if (nearbyIncidents.size() >= HIGH_RISK_INCIDENT_COUNT) {
            return "HIGH";
        } else if (nearbyIncidents.size() >= MODERATE_RISK_INCIDENT_COUNT) {
            return "MODERATE";
        } else {
            return "SAFE";
        }
    }

    // Get risk details
    public String getRiskDetails(Double latitude, Double longitude) {
        List<Incident> nearbyIncidents = incidentRepository.findIncidentsNear(latitude, longitude, DISTANCE_THRESHOLD);
        String riskLevel = checkRiskLevel(latitude, longitude);
        
        StringBuilder details = new StringBuilder();
        details.append("Risk Level: ").append(riskLevel).append("\n");
        details.append("Incidents nearby: ").append(nearbyIncidents.size()).append("\n");
        
        if (!nearbyIncidents.isEmpty()) {
            details.append("Recent incidents in this area:\n");
            nearbyIncidents.stream().limit(5).forEach(incident -> 
                details.append("- ").append(incident.getDescription()).append("\n")
            );
        }
        
        return details.toString();
    }

    // Log risk assessment
    public RiskLog logRiskAssessment(Long userId, Double latitude, Double longitude, String riskLevel) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        RiskLog riskLog = new RiskLog();
        riskLog.setUser(user);
        riskLog.setLatitude(latitude);
        riskLog.setLongitude(longitude);
        riskLog.setRiskLevel(riskLevel);
        riskLog.setReason(getRiskDetails(latitude, longitude));
        
        return riskLogRepository.save(riskLog);
    }

    // Get risk history for a user
    public List<RiskLog> getRiskHistory(Long userId) {
        return riskLogRepository.findByUserId(userId);
    }

    // Get high-risk zones
    public List<RiskLog> getHighRiskZones(Long userId) {
        return riskLogRepository.findByRiskLevel("HIGH");
    }

    // Calculate distance between two points (Haversine formula)
    public double calculateDistance(Double lat1, Double lon1, Double lat2, Double lon2) {
        final int R = 6371; // Radius of the earth in km
        
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c; // distance in km
        
        return distance;
    }
}
