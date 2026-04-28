import React, { useState, useEffect, useCallback } from 'react';
import SOSButton from '../components/SOSButton';
import StopButton from '../components/StopButton';
import AlertDisplay from '../components/AlertDisplay';
import AIChat from '../components/AIChat';
import RiskIndicator from '../components/RiskIndicator';
import { startLocationTracking, stopLocationTracking, getCurrentLocation } from '../services/location';
import {
  saveLocationWithWarning,
  triggerSOS,
  checkRiskLevel
} from '../services/api';
import './Dashboard.css';

function Dashboard({ user, onLogout }) {
  const [location, setLocation] = useState(null);
  const [riskLevel, setRiskLevel] = useState(null);
  const [alert, setAlert] = useState(null);
  const [sosTriggered, setSosTriggered] = useState(false);
  const [isTracking, setIsTracking] = useState(false);

  // Start location tracking on component mount
  useEffect(() => {
    startTracking();
    return () => stopLocationTracking();
  }, []);

  const startTracking = () => {
    if (!isTracking) {
      startLocationTracking(handleLocationUpdate);
      setIsTracking(true);
    }
  };

  const stopTracking = () => {
    stopLocationTracking();
    setIsTracking(false);
  };

  const handleLocationUpdate = useCallback(async (loc) => {
    setLocation(loc);

    // Send location to backend with warning check
    try {
      const response = await saveLocationWithWarning(user.id, {
        latitude: loc.latitude,
        longitude: loc.longitude,
        timestamp: loc.timestamp
      });

      if (response.success) {
        const data = response.data;
        setRiskLevel(data.riskLevel);

        // Show warning if HIGH risk
        if (data.shouldDisplayAlert && data.riskLevel === 'HIGH') {
          setAlert({
            type: 'danger',
            title: '⚠️ DANGER ZONE',
            message: data.alertMessage
          });
        } else if (data.shouldDisplayAlert && data.riskLevel === 'MODERATE') {
          setAlert({
            type: 'warning',
            title: '⚠️ Moderate Risk',
            message: data.alertMessage
          });
        }
      }
    } catch (error) {
      console.error('Error sending location:', error);
    }
  }, [user.id]);

  const handleSOS = async () => {
    if (!location) {
      setAlert({
        type: 'warning',
        title: 'Location Required',
        message: 'Unable to get your location. Please enable location access.'
      });
      return;
    }

    setSosTriggered(true);

    try {
      const response = await triggerSOS(user.id, location.latitude, location.longitude);

      if (response.success) {
        setAlert({
          type: 'danger',
          title: '🚨 SOS TRIGGERED',
          message: 'Emergency alert sent to your emergency contacts!'
        });

        // Reset after 3 seconds
        setTimeout(() => setSosTriggered(false), 3000);
      }
    } catch (error) {
      setAlert({
        type: 'warning',
        title: 'Error',
        message: 'Failed to trigger SOS. Please try again.'
      });
      setSosTriggered(false);
    }
  };

  const handleRiskDetected = (riskData) => {
    if (riskData.riskLevel === 'HIGH') {
      setAlert({
        type: 'danger',
        title: '🚨 HIGH RISK DETECTED',
        message: 'AI detected distress signals. SOS may be triggered automatically.'
      });
    }
  };

  return (
    <div className="dashboard">
      <header className="dashboard-header">
        <div className="header-content">
          <h1>🛡️ Women Safety System</h1>
          <p>Welcome, {user.name}!</p>
        </div>
        <div className="header-actions">
          <span className="location-status">
            {isTracking ? '📍 Tracking' : '📍 Not Tracking'}
          </span>
          <button className="logout-btn" onClick={onLogout}>Logout</button>
        </div>
      </header>

      <AlertDisplay alert={alert} onClose={() => setAlert(null)} />

      <main className="dashboard-main">
        <div className="dashboard-grid">
          {/* SOS Section */}
          <section className="sos-section">
            <div className="section-header">
              <h2>Emergency SOS</h2>
              <p>Press the button for immediate emergency response</p>
            </div>
            <div className="sos-container">
              <SOSButton onSOS={handleSOS} isActive={sosTriggered} />
              <StopButton onStop={stopTracking} isTracking={isTracking} />
            </div>
            {location && (
              <div className="location-display">
                <small>📍 {location.latitude.toFixed(6)}, {location.longitude.toFixed(6)}</small>
                <small>🕐 {location.timestamp?.toLocaleTimeString()}</small>
              </div>
            )}
          </section>

          {/* Risk Indicator */}
          {riskLevel && (
            <section className="risk-section">
              <RiskIndicator 
                riskLevel={riskLevel} 
                latitude={location?.latitude} 
                longitude={location?.longitude}
              />
            </section>
          )}

          {/* AI Chat */}
          <section className="ai-section">
            <AIChat userId={user.id} onRiskDetected={handleRiskDetected} />
          </section>
        </div>

        {/* Tracking Controls */}
        <div className="tracking-controls">
          <button 
            className={`tracking-btn ${isTracking ? 'active' : ''}`}
            onClick={isTracking ? stopTracking : startTracking}
          >
            {isTracking ? '⏸️ Stop Tracking' : '▶️ Start Tracking'}
          </button>
          <small className="tracking-info">
            {isTracking 
              ? '📡 Location is being tracked every 10 seconds'
              : '📴 Location tracking is disabled'}
          </small>
        </div>

        {/* Features Info */}
        <section className="features-section">
          <h2>🎯 Features</h2>
          <div className="features-grid">
            <div className="feature">
              <span>🚨</span>
              <h3>SOS Alert</h3>
              <p>One-tap emergency alert</p>
            </div>
            <div className="feature">
              <span>📍</span>
              <h3>Live Tracking</h3>
              <p>Real-time location updates</p>
            </div>
            <div className="feature">
              <span>⚠️</span>
              <h3>Risk Detection</h3>
              <p>Automatic danger zone alerts</p>
            </div>
            <div className="feature">
              <span>🤖</span>
              <h3>AI Assistant</h3>
              <p>Distress detection & analysis</p>
            </div>
            <div className="feature">
              <span>📱</span>
              <h3>Emergency Contacts</h3>
              <p>Quick contact management</p>
            </div>
            <div className="feature">
              <span>📊</span>
              <h3>Incident Reports</h3>
              <p>Track safety history</p>
            </div>
          </div>
        </section>
      </main>
    </div>
  );
}

export default Dashboard;
