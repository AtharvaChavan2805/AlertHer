package com.womensafety.service;

import com.womensafety.model.Incident;
import com.womensafety.model.User;
import com.womensafety.repository.IncidentRepository;
import com.womensafety.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class IncidentService {

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private UserRepository userRepository;

    // Report incident
    public Incident reportIncident(Long userId, Incident incident) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        incident.setUser(user);
        return incidentRepository.save(incident);
    }

    // Get all incidents for a user
    public List<Incident> getIncidentsByUserId(Long userId) {
        return incidentRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    // Get incident by ID
    public Optional<Incident> getIncidentById(Long id) {
        return incidentRepository.findById(id);
    }

    // Get incidents near a location
    public List<Incident> getIncidentsNear(Double latitude, Double longitude, Double distance) {
        return incidentRepository.findIncidentsNear(latitude, longitude, distance);
    }

    // Delete incident
    public void deleteIncident(Long id) {
        incidentRepository.deleteById(id);
    }

    // Get all incidents
    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }
}
