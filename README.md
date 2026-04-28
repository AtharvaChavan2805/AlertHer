# 🛡️ Women Safety System - Full Stack Project

**An AI-powered safety platform to help women in emergencies with location tracking, SOS alerts, risk detection, and AI-based distress analysis.**

## 🎯 Overview

This project is a comprehensive full-stack application with:
- **Backend:** Spring Boot REST API with MySQL database
- **Frontend:** React single-page application
- **Features:** SOS alerts, location tracking, risk detection, AI analysis, emergency contacts

---

## 📁 Project Structure

```
Women Safety/
├── backend/              # Spring Boot REST API
│   ├── src/main/java/com/womensafety/
│   │   ├── controller/   # REST Controllers
│   │   ├── service/      # Business Logic
│   │   ├── repository/   # Data Access
│   │   └── model/        # JPA Entities
│   ├── pom.xml
│   └── README.md
├── frontend/             # React Application
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── services/
│   │   └── App.js
│   ├── package.json
│   └── README.md
└── README.md            # This file
```

---

## 🔧 Tech Stack

### Backend
- **Framework:** Spring Boot 3.1.5
- **Language:** Java 17
- **Build:** Maven
- **Database:** MySQL 8.0
- **ORM:** JPA/Hibernate
- **Security:** Spring Security + JWT
- **Additional:** Lombok, Validation

### Frontend
- **Framework:** React 18.2.0
- **Styling:** CSS3
- **HTTP:** Fetch API
- **Node:** 16+

---

## 📋 Implementation Phases

### ✅ PHASE 1: PROJECT SETUP (COMPLETED)
- Created backend Spring Boot structure
- Created frontend React structure
- Set up Maven dependencies
- Configured MySQL connection properties

### 📌 PHASE 2: DATABASE + ENTITIES (NEXT)
- Create JPA entities for all tables
- Set up relationships (One-to-Many)
- Configure database mappings

### 📌 PHASE 3: BASIC CRUD APIs
- User registration & login
- Emergency contacts management
- Incident reporting

### 📌 PHASE 4: LOCATION TRACKING
- Store user location every 10 seconds
- Create location tracking API

### 📌 PHASE 5: SOS SYSTEM
- Create SOS alert system
- Fetch emergency contacts
- Send notifications

### 📌 PHASE 6: RED ZONE DETECTION
- Analyze incident clusters
- Identify high-risk areas
- Calculate risk levels

### 📌 PHASE 7: WARNING SYSTEM
- Modify location API with risk checks
- Display warnings for high-risk zones

### 📌 PHASE 8: AI INTEGRATION
- Integrate AI API for distress detection
- Auto-trigger SOS for high-risk messages

### 📌 PHASE 9: REACT FRONTEND
- Create UI components
- Implement SOS button
- Add location tracking UI

### 📌 PHASE 10: FRONTEND LOGIC
- Location tracking JavaScript
- Warning display logic
- AI message input

### 📌 PHASE 11: FINAL INTEGRATION
- Connect frontend ↔ backend
- Full system testing
- Deployment preparation

---

## 🚀 Quick Start

### Backend Setup

1. **Install MySQL:**
   - Download MySQL 8.0
   - Create database: `women_safety_db`

2. **Update Database Config:**
   ```bash
   cd backend
   # Edit src/main/resources/application.properties
   # Update spring.datasource.username and password
   ```

3. **Run Backend:**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
   - Server runs on: `http://localhost:8080/api`

### Frontend Setup

1. **Install Dependencies:**
   ```bash
   cd frontend
   npm install
   ```

2. **Start Development Server:**
   ```bash
   npm start
   ```
   - App runs on: `http://localhost:3000`

---

## 📊 Database Schema (Phase 2)

Will include tables for:
- `users` - User accounts
- `emergency_contacts` - Emergency contacts per user
- `locations` - Location history
- `sos_alerts` - SOS emergency alerts
- `incidents` - Incident reports
- `risk_logs` - Risk assessment logs
- `ai_logs` - AI analysis logs

---

## 🔐 Security Features

- JWT-based authentication
- Password encryption
- Input validation
- CORS configuration

---

## 📝 API Documentation

All endpoints will be documented with:
- Request/Response formats
- Authentication requirements
- Example curl commands

See individual phase READMEs for detailed API docs.

---

## 🛠️ Development Workflow

1. Backend development with Spring Boot
2. Database testing with MySQL
3. Frontend component development
4. Frontend-Backend integration
5. End-to-end testing
6. Deployment

---

## 📱 Core Features (Final)

✅ User Registration & Login  
✅ Emergency Contact Management  
✅ SOS Alert System  
✅ Live Location Tracking (10s intervals)  
✅ Red Zone Detection  
✅ Risk Level Warnings  
✅ Incident Reporting  
✅ AI-Powered Distress Detection  
✅ Automatic SOS Triggering  
✅ Risk Assessment & Analytics  

---

## 🤝 Contributing

This is a development project. Follow the phase-by-phase approach for implementations.

---

## 📞 Support

For issues or questions, refer to individual module READMEs.

---

## 📄 License

This project is for educational and women safety purposes.

---

**Happy Coding! 🚀 Let's build a safer community together! 🛡️**
