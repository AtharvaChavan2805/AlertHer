package com.womensafety.dto;

public class LocationWithWarningResponse {
    private Long locationId;
    private Double latitude;
    private Double longitude;
    private String riskLevel;
    private String warning;
    private boolean shouldDisplayAlert;
    private String alertMessage;

    // Constructors
    public LocationWithWarningResponse() {
    }

    public LocationWithWarningResponse(Long locationId, Double latitude, Double longitude, String riskLevel, String warning, boolean shouldDisplayAlert, String alertMessage) {
        this.locationId = locationId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.riskLevel = riskLevel;
        this.warning = warning;
        this.shouldDisplayAlert = shouldDisplayAlert;
        this.alertMessage = alertMessage;
    }

    // Getters and Setters
    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public boolean isShouldDisplayAlert() {
        return shouldDisplayAlert;
    }

    public void setShouldDisplayAlert(boolean shouldDisplayAlert) {
        this.shouldDisplayAlert = shouldDisplayAlert;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }
}
