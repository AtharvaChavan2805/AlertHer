import React from 'react';
import './RiskIndicator.css';

function RiskIndicator({ riskLevel, latitude, longitude }) {
  const getRiskColor = (level) => {
    switch (level) {
      case 'HIGH':
        return '#ff4444';
      case 'MODERATE':
        return '#ffc107';
      case 'SAFE':
        return '#28a745';
      default:
        return '#999';
    }
  };

  const getRiskEmoji = (level) => {
    switch (level) {
      case 'HIGH':
        return '🚨';
      case 'MODERATE':
        return '⚠️';
      case 'SAFE':
        return '✅';
      default:
        return 'ℹ️';
    }
  };

  return (
    <div className="risk-indicator">
      <div className="risk-display">
        <div className="risk-emoji">{getRiskEmoji(riskLevel)}</div>
        <div className="risk-info">
          <h3>Risk Level</h3>
          <p className="risk-level" style={{ color: getRiskColor(riskLevel) }}>
            {riskLevel || 'UNKNOWN'}
          </p>
        </div>
      </div>
      <div className="location-info">
        <small>📍 {latitude?.toFixed(4)}, {longitude?.toFixed(4)}</small>
      </div>
    </div>
  );
}

export default RiskIndicator;
