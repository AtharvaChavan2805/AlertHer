package com.womensafety.service;

import com.womensafety.model.SOSAlert;
import com.womensafety.model.EmergencyContact;
import com.womensafety.model.User;
import com.womensafety.repository.SOSAlertRepository;
import com.womensafety.repository.UserRepository;
import com.womensafety.repository.EmergencyContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SOSAlertService {

    @Autowired
    private SOSAlertRepository sosAlertRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmergencyContactRepository contactRepository;

    @Autowired
    private SosNotificationService sosNotificationService;

    // Trigger SOS alert
    public SOSAlert triggerSOS(Long userId, Double latitude, Double longitude) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        SOSAlert sosAlert = new SOSAlert();
        sosAlert.setUser(user);
        sosAlert.setLatitude(latitude);
        sosAlert.setLongitude(longitude);
        sosAlert.setStatus("TRIGGERED");
        
        SOSAlert savedAlert = sosAlertRepository.save(sosAlert);
        
        // Fetch emergency contacts and send real notifications (if configured)
        List<EmergencyContact> contacts = contactRepository.findByUserId(userId);

        sosNotificationService.dispatchSOS(user, latitude, longitude, savedAlert.getCreatedAt(), contacts);
        
        return savedAlert;
    }

    // Get all SOS alerts for a user
    public List<SOSAlert> getSOSAlertsByUserId(Long userId) {
        return sosAlertRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    // Update SOS alert status
    public SOSAlert updateSOSAlertStatus(Long alertId, String status) {
        SOSAlert alert = sosAlertRepository.findById(alertId)
                .orElseThrow(() -> new RuntimeException("SOS Alert not found"));
        
        alert.setStatus(status);
        return sosAlertRepository.save(alert);
    }

    // Get all active SOS alerts
    public List<SOSAlert> getActiveSOSAlerts() {
        return sosAlertRepository.findByStatus("ACTIVE");
    }

    // Cancel SOS alert
    public SOSAlert cancelSOSAlert(Long alertId) {
        return updateSOSAlertStatus(alertId, "CANCELLED");
    }

    // Resolve SOS alert
    public SOSAlert resolveSOSAlert(Long alertId) {
        return updateSOSAlertStatus(alertId, "RESOLVED");
    }
}
