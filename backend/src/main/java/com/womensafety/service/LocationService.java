package com.womensafety.service;

import com.womensafety.model.Location;
import com.womensafety.model.User;
import com.womensafety.repository.LocationRepository;
import com.womensafety.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private UserRepository userRepository;

    // Save location
    public Location saveLocation(Long userId, Location location) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        location.setUser(user);
        if (location.getTimestamp() == null) {
            location.setTimestamp(LocalDateTime.now());
        }
        return locationRepository.save(location);
    }

    // Get all locations for a user
    public List<Location> getLocationsByUserId(Long userId) {
        return locationRepository.findByUserIdOrderByTimestampDesc(userId);
    }

    // Get recent locations (last hour)
    public List<Location> getRecentLocations(Long userId) {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        return locationRepository.findRecentLocations(userId, oneHourAgo);
    }

    // Get latest location for a user
    public Optional<Location> getLatestLocation(Long userId) {
        List<Location> locations = locationRepository.findByUserIdOrderByTimestampDesc(userId);
        return locations.isEmpty() ? Optional.empty() : Optional.of(locations.get(0));
    }

    // Get locations near a point
    public List<Location> getLocationsNear(Double latitude, Double longitude, Double distance) {
        return locationRepository.findLocationsNear(latitude, longitude, distance);
    }

    // Delete old locations (older than 30 days)
    public void deleteOldLocations() {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        List<Location> allLocations = locationRepository.findAll();
        
        allLocations.stream()
                .filter(loc -> loc.getCreatedAt().isBefore(thirtyDaysAgo))
                .forEach(loc -> locationRepository.delete(loc));
    }
}
