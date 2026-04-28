import React, { useState, useEffect } from 'react';
import './AlertDisplay.css';

function AlertDisplay({ alert, onClose }) {
  useEffect(() => {
    if (alert) {
      const timer = setTimeout(onClose, 5000); // Auto-close after 5 seconds
      return () => clearTimeout(timer);
    }
  }, [alert, onClose]);

  if (!alert) return null;

  return (
    <div className={`alert alert-${alert.type}`}>
      <div className="alert-content">
        <span className="alert-icon">
          {alert.type === 'warning' && '⚠️'}
          {alert.type === 'danger' && '🚨'}
          {alert.type === 'success' && '✅'}
          {alert.type === 'info' && 'ℹ️'}
        </span>
        <div className="alert-message">
          <h3>{alert.title}</h3>
          <p>{alert.message}</p>
        </div>
        <button className="alert-close" onClick={onClose}>×</button>
      </div>
      <div className="alert-progress"></div>
    </div>
  );
}

export default AlertDisplay;
