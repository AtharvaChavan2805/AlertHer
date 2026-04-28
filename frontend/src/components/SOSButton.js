import React from 'react';
import './SOSButton.css';

function SOSButton({ onSOS, isActive }) {
  return (
    <button className={`sos-button ${isActive ? 'pulse' : ''}`} onClick={onSOS} title="Trigger Emergency SOS">
      <div className="sos-inner">
        <span className="sos-text">SOS</span>
        <div className="sos-circles">
          <div className="circle circle-1"></div>
          <div className="circle circle-2"></div>
          <div className="circle circle-3"></div>
        </div>
      </div>
    </button>
  );
}

export default SOSButton;
