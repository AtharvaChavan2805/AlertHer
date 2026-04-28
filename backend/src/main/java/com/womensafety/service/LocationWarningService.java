package com.womensafety.service;

import com.womensafety.dto.LocationWithWarningResponse;
import com.womensafety.model.Location;
import com.womensafety.model.RiskLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationWarningService {

    @Autowired
    private LocationService locationService;

    @Autowired
    private RiskDetectionService riskDetectionService;

    // Save location and return warning if necessary
    public LocationWithWarningResponse saveLocationWithWarning(Long userId, Location location) {
        // Save location
        Location savedLocation = locationService.saveLocation(userId, location);
        
        // Check risk level
        String riskLevel = riskDetectionService.checkRiskLevel(location.getLatitude(), location.getLongitude());
        
        // Log risk assessment
        riskDetectionService.logRiskAssessment(userId, location.getLatitude(), location.getLongitude(), riskLevel);
        
        // Create response
        LocationWithWarningResponse response = new LocationWithWarningResponse();
        response.setLocationId(savedLocation.getId());
        response.setLatitude(location.getLatitude());
        response.setLongitude(location.getLongitude());
        response.setRiskLevel(riskLevel);
        
        // Set warning based on risk level
        if ("HIGH".equals(riskLevel)) {
            response.setShouldDisplayAlert(true);
            response.setAlertMessage("⚠️ WARNING: You are entering a HIGH RISK ZONE! Please be careful and consider moving to a safer location.");
            response.setWarning("HIGH_RISK_ZONE");
        } else if ("MODERATE".equals(riskLevel)) {
            response.setShouldDisplayAlert(true);
            response.setAlertMessage("⚠️ CAUTION: You are in a MODERATE RISK area. Stay alert and keep your phone nearby.");
            response.setWarning("MODERATE_RISK_ZONE");
        } else {
            response.setShouldDisplayAlert(false);
            response.setAlertMessage("✅ This area appears to be safe.");
            response.setWarning("SAFE");
        }
        
        return response;
    }
}
