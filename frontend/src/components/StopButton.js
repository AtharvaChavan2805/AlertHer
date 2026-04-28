import React from 'react';
import './StopButton.css';

function StopButton({ onStop, isTracking }) {
  return (
    <button 
      className={`stop-button ${isTracking ? 'active' : 'inactive'}`} 
      onClick={onStop} 
      title="Stop location sharing"
      disabled={!isTracking}
    >
      <div className="stop-inner">
        <span className="stop-text">⏹ STOP</span>
        <span className="stop-label">Tracking</span>
      </div>
    </button>
  );
}

export default StopButton;
