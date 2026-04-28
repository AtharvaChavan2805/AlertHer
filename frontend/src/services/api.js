// API service for backend calls
const API_BASE_URL = 'http://localhost:8082/api';

export const apiCall = async (endpoint, method = 'GET', data = null) => {
  const options = {
    method,
    headers: {
      'Content-Type': 'application/json',
    },
  };

  if (data) {
    options.body = JSON.stringify(data);
  }

  const response = await fetch(`${API_BASE_URL}${endpoint}`, options);
  return response.json();
};

// User APIs
export const registerUser = (user) => 
  apiCall('/users/register', 'POST', user);

export const loginUser = (email, password) => 
  apiCall('/users/login', 'POST', { email, password });

export const getUser = (userId) => 
  apiCall(`/users/${userId}`);

// Location APIs
export const saveLocation = (userId, locationData) => 
  apiCall(`/location?userId=${userId}`, 'POST', { 
    latitude: locationData.latitude, 
    longitude: locationData.longitude
  });

export const saveLocationWithWarning = (userId, locationData) => 
  apiCall(`/location/with-warning?userId=${userId}`, 'POST', { 
    latitude: locationData.latitude, 
    longitude: locationData.longitude
  });

export const getLocationHistory = (userId) => 
  apiCall(`/location/${userId}`);

// SOS APIs
export const triggerSOS = (userId, latitude, longitude) => 
  apiCall(`/sos/trigger?userId=${userId}&latitude=${latitude}&longitude=${longitude}`, 'POST');

export const getSOSAlerts = (userId) => 
  apiCall(`/sos/${userId}`);

// Risk APIs
export const checkRiskLevel = (latitude, longitude) => 
  apiCall(`/risk/check?latitude=${latitude}&longitude=${longitude}`);

export const getRiskHistory = (userId) => 
  apiCall(`/risk/history/${userId}`);

// AI APIs
export const analyzeDistress = (userId, message) => 
  apiCall(`/ai/analyze?userId=${userId}&message=${encodeURIComponent(message)}`, 'POST');

export const getAIHistory = (userId) => 
  apiCall(`/ai/history/${userId}`);

// Emergency Contacts APIs
export const addEmergencyContact = (userId, contact) => 
  apiCall(`/contacts/${userId}`, 'POST', contact);

export const getEmergencyContacts = (userId) => 
  apiCall(`/contacts/${userId}`);

export const deleteEmergencyContact = (contactId) => 
  apiCall(`/contacts/${contactId}`, 'DELETE');

// Incident APIs
export const reportIncident = (userId, incident) => 
  apiCall(`/incident?userId=${userId}`, 'POST', incident);

export const getIncidents = (userId) => 
  apiCall(`/incident?userId=${userId}`);
