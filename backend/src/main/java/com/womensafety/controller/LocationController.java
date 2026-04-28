package com.womensafety.controller;

import com.womensafety.dto.ApiResponse;
import com.womensafety.dto.LocationWithWarningResponse;
import com.womensafety.model.Location;
import com.womensafety.service.LocationService;
import com.womensafety.service.LocationWarningService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/location")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private LocationWarningService locationWarningService;

    // Save location (Called every 10 seconds)
    @PostMapping
    public ResponseEntity<ApiResponse> saveLocation(@RequestParam Long userId, 
                                                    @Valid @RequestBody Location location) {
        try {
            Location savedLocation = locationService.saveLocation(userId, location);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Location saved successfully", savedLocation));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    // Get all locations for a user
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getLocationsByUserId(@PathVariable Long userId) {
        try {
            List<Location> locations = locationService.getLocationsByUserId(userId);
            return ResponseEntity.ok(new ApiResponse(true, "Locations retrieved", locations));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    // Get recent locations (last hour)
    @GetMapping("/recent/{userId}")
    public ResponseEntity<ApiResponse> getRecentLocations(@PathVariable Long userId) {
        try {
            List<Location> locations = locationService.getRecentLocations(userId);
            return ResponseEntity.ok(new ApiResponse(true, "Recent locations retrieved", locations));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    // Get latest location
    @GetMapping("/latest/{userId}")
    public ResponseEntity<ApiResponse> getLatestLocation(@PathVariable Long userId) {
        try {
            Location location = locationService.getLatestLocation(userId)
                    .orElseThrow(() -> new RuntimeException("No location found"));
            return ResponseEntity.ok(new ApiResponse(true, "Latest location retrieved", location));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    // Get locations near a point
    @GetMapping("/near")
    public ResponseEntity<ApiResponse> getLocationsNear(@RequestParam Double latitude, 
                                                        @RequestParam Double longitude, 
                                                        @RequestParam Double distance) {
        try {
            List<Location> locations = locationService.getLocationsNear(latitude, longitude, distance);
            return ResponseEntity.ok(new ApiResponse(true, "Nearby locations retrieved", locations));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    // Save location with warning (PHASE 7)
    @PostMapping("/with-warning")
    public ResponseEntity<ApiResponse> saveLocationWithWarning(@RequestParam Long userId, 
                                                               @Valid @RequestBody Location location) {
        try {
            LocationWithWarningResponse response = locationWarningService.saveLocationWithWarning(userId, location);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Location saved with risk assessment", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
}
