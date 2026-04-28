# 🎯 Quick Start Guide

## ⚡ 5-Minute Quick Start

### Prerequisites Installed?
- ✅ Java 17+
- ✅ Maven 3.6+
- ✅ MySQL 8.0+
- ✅ Node.js 16+

### Step 1: Database Setup (1 min)
```bash
# Open MySQL
mysql -u root -p

# Create database (paste these commands):
CREATE DATABASE women_safety_db;
USE women_safety_db;
EXIT;
```

### Step 2: Start Backend (2 min)
```bash
cd "d:\Women Safety\backend"
mvn spring-boot:run
```
Wait for: `Started WomenSafetyApplication`

### Step 3: Start Frontend (1 min)
```bash
cd "d:\Women Safety\frontend"
npm install  # Only first time
npm start
```
Browser opens at `http://localhost:3000`

### Step 4: Test!
1. Register: sarah@example.com / password123
2. Allow location access
3. Click "Start Tracking"
4. Press SOS button
5. Type message in AI chat

---

## 📋 What's Included?

### Backend (Spring Boot)
| Component | Purpose |
|-----------|---------|
| Controllers | REST API endpoints |
| Services | Business logic |
| Repositories | Database access |
| Models | JPA entities |
| DTOs | Data transfer objects |

### Frontend (React)
| Component | Purpose |
|-----------|---------|
| Pages | Login, Dashboard |
| Components | SOS, Alert, Chat, Risk |
| Services | API, Location tracking |
| Styling | CSS3 animations |

### Database (MySQL)
7 Tables: users, emergency_contacts, locations, sos_alerts, incidents, risk_logs, ai_logs

---

## 🔑 Key Features

**SOS Alert System** 🚨
- One-tap emergency alert
- Notifies emergency contacts
- Records location

**Location Tracking** 📍
- Every 10 seconds automatically
- Stored in database
- Used for risk assessment

**Risk Detection** ⚠️
- Analyzes nearby incidents
- Shows SAFE/MODERATE/HIGH
- Auto-warns user

**AI Analysis** 🤖
- Analyzes distress messages
- Detects keywords
- Auto-triggers SOS if HIGH

**Emergency Management** 👥
- Add emergency contacts
- Report incidents
- View history

---

## 🚀 Troubleshooting

**MySQL not starting?**
```
Windows: services.msc → Find MySQL → Start
Mac: brew services start mysql
Linux: sudo systemctl start mysql
```

**Port 8080 in use?**
```
Edit: backend/src/main/resources/application.properties
Change: server.port=8081
```

**Port 3000 in use?**
```
Windows: taskkill /PID <PID> /F
Mac: lsof -i :3000 | awk 'NR!=1 {print $2}' | xargs kill -9
```

**Dependencies not installing?**
```
Delete: node_modules & package-lock.json
Run: npm install
```

---

## 📞 Support Files

- `SETUP_GUIDE.md` - Detailed setup
- `API_DOCUMENTATION.md` - All endpoints
- `INTEGRATION_GUIDE.md` - Full integration

---

## 🎉 You're Ready!

Open http://localhost:3000 and start testing! 🛡️
