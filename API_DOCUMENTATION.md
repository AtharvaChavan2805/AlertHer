# API Documentation

## 🌐 Women Safety System - REST API Reference

**Base URL:** `http://localhost:8080/api`

---

## 👤 User APIs

### Register User
- **Endpoint:** `POST /users/register`
- **Description:** Create new user account
- **Request Body:**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "phone": "+1234567890"
}
```
- **Response (200):**
```json
{
  "success": true,
  "message": "User registered successfully",
  "data": {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "phone": "+1234567890",
    "createdAt": "2024-01-15T10:30:00"
  }
}
```

### Login User
- **Endpoint:** `POST /users/login`
- **Request Body:**
```json
{
  "email": "john@example.com",
  "password": "password123"
}
```
- **Response (200):**
```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "phone": "+1234567890"
  }
}
```

### Get User Details
- **Endpoint:** `GET /users/{userId}`
- **Example:** `GET /users/1`
- **Response (200):**
```json
{
  "success": true,
  "message": "User found",
  "data": {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "phone": "+1234567890"
  }
}
```

### Update User
- **Endpoint:** `PUT /users/{userId}`
- **Request Body:**
```json
{
  "name": "Jane Doe",
  "phone": "+9876543210"
}
```

### Delete User
- **Endpoint:** `DELETE /users/{userId}`

---

## 🆘 SOS Alert APIs (PHASE 5)

### Trigger SOS
- **Endpoint:** `POST /sos/trigger`
- **Parameters:**
  - `userId` (required)
  - `latitude` (required)
  - `longitude` (required)
- **Example:** `POST /sos/trigger?userId=1&latitude=28.6139&longitude=77.2090`
- **Response (201):**
```json
{
  "success": true,
  "message": "SOS Alert triggered successfully",
  "data": {
    "id": 1,
    "userId": 1,
    "latitude": 28.6139,
    "longitude": 77.2090,
    "status": "TRIGGERED",
    "createdAt": "2024-01-15T10:35:00"
  }
}
```
**Actions Performed:**
- ✅ SOS alert saved to database
- ✅ Emergency contacts fetched
- ✅ Alerts sent (simulated)
- ✅ Location recorded

### Get SOS Alerts
- **Endpoint:** `GET /sos/{userId}`
- **Response (200):**
```json
{
  "success": true,
  "message": "SOS Alerts retrieved",
  "data": [
    {
      "id": 1,
      "latitude": 28.6139,
      "longitude": 77.2090,
      "status": "TRIGGERED",
      "createdAt": "2024-01-15T10:35:00"
    }
  ]
}
```

### Update SOS Status
- **Endpoint:** `PUT /sos/{alertId}/status?status=RESOLVED`
- **Allowed Status:** TRIGGERED, ACTIVE, RESOLVED, CANCELLED

### Cancel SOS
- **Endpoint:** `PUT /sos/{alertId}/cancel`

### Resolve SOS
- **Endpoint:** `PUT /sos/{alertId}/resolve`

---

## 📍 Location APIs (PHASE 4)

### Save Location
- **Endpoint:** `POST /location?userId=1`
- **Request Body:**
```json
{
  "latitude": 28.6139,
  "longitude": 77.2090,
  "timestamp": "2024-01-15T10:36:00"
}
```
- **Response (201):**
```json
{
  "success": true,
  "message": "Location saved successfully",
  "data": {
    "id": 1,
    "latitude": 28.6139,
    "longitude": 77.2090,
    "timestamp": "2024-01-15T10:36:00"
  }
}
```
**Called Every 10 Seconds from Frontend**

### Save Location with Warning (PHASE 7)
- **Endpoint:** `POST /location/with-warning?userId=1`
- **Response (201):**
```json
{
  "success": true,
  "message": "Location saved with risk assessment",
  "data": {
    "locationId": 1,
    "latitude": 28.6139,
    "longitude": 77.2090,
    "riskLevel": "HIGH",
    "shouldDisplayAlert": true,
    "alertMessage": "⚠️ WARNING: You are entering a HIGH RISK ZONE!",
    "warning": "HIGH_RISK_ZONE"
  }
}
```

### Get Location History
- **Endpoint:** `GET /location/{userId}`
- **Response (200):**
```json
{
  "success": true,
  "message": "Locations retrieved",
  "data": [
    {
      "id": 1,
      "latitude": 28.6139,
      "longitude": 77.2090,
      "timestamp": "2024-01-15T10:36:00"
    }
  ]
}
```

### Get Recent Locations (Last Hour)
- **Endpoint:** `GET /location/recent/{userId}`

### Get Latest Location
- **Endpoint:** `GET /location/latest/{userId}`

### Get Nearby Locations
- **Endpoint:** `GET /location/near?latitude=28.6139&longitude=77.2090&distance=0.05`

---

## ⚠️ Risk Detection APIs (PHASE 6)

### Check Risk Level
- **Endpoint:** `GET /risk/check?latitude=28.6139&longitude=77.2090`
- **Response (200):**
```json
{
  "success": true,
  "message": "Risk assessment completed",
  "data": {
    "riskLevel": "HIGH",
    "details": "Risk Level: HIGH\nIncidents nearby: 8\nRecent incidents in this area:\n- Robbery reported\n- Assault case\n- Harassment incident\n..."
  }
}
```
**Risk Levels:** SAFE, MODERATE, HIGH

### Get Risk History
- **Endpoint:** `GET /risk/history/{userId}`

### Get High-Risk Zones
- **Endpoint:** `GET /risk/high-zones/{userId}`

---

## 🤖 AI Analysis APIs (PHASE 8)

### Analyze Distress Message
- **Endpoint:** `POST /ai/analyze?userId=1&message=I%20feel%20unsafe`
- **Request:** URL-encoded message parameter
- **Response (200):**
```json
{
  "success": true,
  "message": "AI analysis completed",
  "data": {
    "riskLevel": "HIGH",
    "message": "🚨 CRITICAL: High-risk distress signal detected.",
    "autoSOSTriggered": true
  }
}
```
**Risk Detection Keywords:**
- **HIGH RISK:** help, danger, emergency, attack, unsafe, threatened, harassment, assault, scared, terrified, distress
- **MODERATE RISK:** worried, anxious, concerned, uncomfortable, lost, alone

### Get AI Analysis History
- **Endpoint:** `GET /ai/history/{userId}`

### Get High-Risk AI Logs
- **Endpoint:** `GET /ai/high-risk`

---

## 📞 Emergency Contact APIs (PHASE 3)

### Add Emergency Contact
- **Endpoint:** `POST /contacts/{userId}`
- **Request Body:**
```json
{
  "name": "Mom",
  "phone": "+9876543210",
  "relationship": "Mother"
}
```
- **Response (201):**
```json
{
  "success": true,
  "message": "Contact added successfully",
  "data": {
    "id": 1,
    "name": "Mom",
    "phone": "+9876543210",
    "relationship": "Mother"
  }
}
```

### Get Emergency Contacts
- **Endpoint:** `GET /contacts/{userId}`

### Update Contact
- **Endpoint:** `PUT /contacts/{contactId}`

### Delete Contact
- **Endpoint:** `DELETE /contacts/{contactId}`

---

## 📋 Incident APIs (PHASE 3)

### Report Incident
- **Endpoint:** `POST /incident?userId=1`
- **Request Body:**
```json
{
  "description": "Suspicious person following me",
  "latitude": 28.6139,
  "longitude": 77.2090
}
```
- **Response (201):**
```json
{
  "success": true,
  "message": "Incident reported successfully",
  "data": {
    "id": 1,
    "userId": 1,
    "description": "Suspicious person following me",
    "latitude": 28.6139,
    "longitude": 77.2090,
    "createdAt": "2024-01-15T10:40:00"
  }
}
```

### Get Incidents
- **Endpoint:** `GET /incident?userId=1`

### Get Incident Details
- **Endpoint:** `GET /incident/{incidentId}`

### Delete Incident
- **Endpoint:** `DELETE /incident/{incidentId}`

---

## 🔄 HTTP Status Codes

| Code | Meaning |
|------|---------|
| 200 | OK - Request successful |
| 201 | Created - Resource created successfully |
| 400 | Bad Request - Invalid input |
| 401 | Unauthorized - Authentication required |
| 404 | Not Found - Resource not found |
| 500 | Internal Server Error - Server error |

---

## 📝 Error Response Format

All error responses follow this format:

```json
{
  "success": false,
  "message": "Error description",
  "data": null
}
```

---

## 🧪 Example cURL Commands

### Register User
```bash
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Sarah Khan",
    "email": "sarah@example.com",
    "password": "secure123",
    "phone": "+919876543210"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/users/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "sarah@example.com",
    "password": "secure123"
  }'
```

### Trigger SOS
```bash
curl -X POST "http://localhost:8080/api/sos/trigger?userId=1&latitude=28.6139&longitude=77.2090" \
  -H "Content-Type: application/json"
```

### Check Risk Level
```bash
curl -X GET "http://localhost:8080/api/risk/check?latitude=28.6139&longitude=77.2090"
```

### Analyze Message
```bash
curl -X POST "http://localhost:8080/api/ai/analyze?userId=1&message=I%20feel%20unsafe" \
  -H "Content-Type: application/json"
```

---

## 💡 Notes

1. **Authentication:** Currently, all APIs are open. In production, add JWT authentication.
2. **Rate Limiting:** Implement rate limiting to prevent abuse.
3. **Validation:** All inputs are validated server-side.
4. **CORS:** Enabled for all origins (Change in production).
5. **Location Tracking:** Frontend calls location API every 10 seconds.

---

**Last Updated:** January 2024
**API Version:** 1.0.0
