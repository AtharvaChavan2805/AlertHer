# Setup & Installation Guide

## 🎯 Complete Setup Instructions for Women Safety System

### Prerequisites

**System Requirements:**
- Windows 10/11, macOS, or Linux
- 4GB+ RAM
- 2GB free disk space

**Software Required:**
- Java 17+ ([Download](https://www.oracle.com/java/technologies/downloads/#java17))
- Maven 3.6+ ([Download](https://maven.apache.org/download.cgi))
- MySQL 8.0+ ([Download](https://dev.mysql.com/downloads/mysql/))
- Node.js 16+ ([Download](https://nodejs.org/))
- Git ([Download](https://git-scm.com/))

---

## 📦 STEP 1: Database Setup

### 1.1 Install MySQL

**Windows:**
1. Download MySQL installer from https://dev.mysql.com/downloads/mysql/
2. Run the installer and follow the installation wizard
3. Choose "MySQL Server 8.0" during setup
4. Keep default port: 3306
5. Set root password during configuration

**macOS:**
```bash
brew install mysql
brew services start mysql
```

**Linux (Ubuntu/Debian):**
```bash
sudo apt-get update
sudo apt-get install mysql-server
sudo mysql_secure_installation
```

### 1.2 Create Database

Open MySQL and run:

```sql
-- Create database
CREATE DATABASE women_safety_db;

-- Select database
USE women_safety_db;

-- Verify
SHOW TABLES;
```

---

## 🚀 STEP 2: Backend Setup (Spring Boot)

### 2.1 Navigate to Backend Directory

```bash
cd "d:\Women Safety\backend"
```

### 2.2 Update Database Configuration

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/women_safety_db
spring.datasource.username=root
spring.datasource.password=your_mysql_password
spring.jpa.hibernate.ddl-auto=create-drop  # First run: create-drop, then change to update
```

### 2.3 Build Backend

```bash
# Clean and build
mvn clean install

# This will download all dependencies (takes 2-3 minutes on first run)
```

### 2.4 Run Backend

```bash
# Option 1: Using Maven
mvn spring-boot:run

# Option 2: Using JAR file (after building)
java -jar target/women-safety-api-1.0.0.jar
```

**Expected Output:**
```
Started WomenSafetyApplication in X.XXX seconds
```

**Backend Server:** http://localhost:8080/api

---

## ⚛️ STEP 3: Frontend Setup (React)

### 3.1 Navigate to Frontend Directory

```bash
cd "d:\Women Safety\frontend"
```

### 3.2 Install Dependencies

```bash
# Install all npm packages
npm install

# This will take 2-3 minutes
```

### 3.3 Start Development Server

```bash
npm start
```

**Frontend Server:** http://localhost:3000

The browser should automatically open the application.

---

## ✅ STEP 4: Verify Installation

### 4.1 Test Backend APIs

**Using curl or Postman:**

```bash
# Test user registration
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test User",
    "email": "test@example.com",
    "password": "password123",
    "phone": "+1234567890"
  }'

# Expected Response:
# {
#   "success": true,
#   "message": "User registered successfully",
#   "data": {
#     "id": 1,
#     "name": "Test User",
#     "email": "test@example.com",
#     ...
#   }
# }
```

### 4.2 Test Frontend

1. Open http://localhost:3000 in browser
2. Click "Register" tab
3. Fill in form:
   - Name: Test User
   - Email: test@example.com
   - Phone: +1234567890
   - Password: password123
4. Click Register
5. Should login automatically or show login page

---

## 🔧 Important Configuration Changes

### Backend Configuration

**File:** `backend/src/main/resources/application.properties`

| Parameter | Default | Description |
|-----------|---------|-------------|
| `spring.datasource.url` | localhost:3306 | MySQL connection URL |
| `spring.datasource.username` | root | MySQL username |
| `spring.datasource.password` | root | MySQL password |
| `server.port` | 8080 | Backend API port |
| `spring.jpa.hibernate.ddl-auto` | update | Database schema auto-update |

### Frontend Configuration

**File:** `frontend/src/services/api.js`

```javascript
const API_BASE_URL = 'http://localhost:8080/api';
```

Change this if backend runs on different host/port.

---

## 🐛 Troubleshooting

### Backend Issues

**Error: "Connection refused" when connecting to MySQL**
```
Solution: 
1. Check if MySQL is running: mysql -u root -p
2. Verify credentials in application.properties
3. Ensure database exists: CREATE DATABASE women_safety_db;
```

**Error: "Port 8080 already in use"**
```
Solution:
1. Change port in application.properties:
   server.port=8081
2. OR kill process using port 8080:
   Windows: netstat -ano | findstr :8080
   Linux/Mac: lsof -i :8080
```

**Error: "Maven build failing"**
```
Solution:
1. Clear Maven cache: mvn clean
2. Update Maven: mvn -v (should be 3.6+)
3. Check Java version: java -version (should be 17+)
```

### Frontend Issues

**Error: "Cannot find module"**
```
Solution:
1. Delete node_modules folder
2. Delete package-lock.json
3. Run: npm install
```

**Error: "Port 3000 already in use"**
```
Solution:
1. Kill process: netstat -ano | findstr :3000 (Windows)
2. OR use different port: PORT=3001 npm start
```

**Geolocation not working in browser:**
```
Solution:
1. Use HTTPS or localhost
2. Allow location permission when browser asks
3. Check browser privacy settings
```

---

## 📱 Test User Credentials

After setup, use these for testing:

**Test Account 1:**
- Email: `test@example.com`
- Password: `password123`

**Test Account 2:**
- Email: `user@example.com`
- Password: `secure123`

---

## 📊 API Testing with Postman

1. Download Postman: https://www.postman.com/downloads/
2. Create new collection "Women Safety System"
3. Import endpoints from endpoints list (see API_DOCUMENTATION.md)
4. Test each endpoint with sample data

---

## 🚀 Deployment (Optional)

### Backend Deployment (Heroku)

```bash
# 1. Create Heroku account
# 2. Install Heroku CLI
# 3. Login
heroku login

# 4. Create app
heroku create your-app-name

# 5. Add MySQL database
heroku addons:create cleardb:ignite

# 6. Deploy
git push heroku main
```

### Frontend Deployment (Netlify)

```bash
# 1. Build production
npm run build

# 2. Deploy to Netlify
npm install -g netlify-cli
netlify deploy --prod --dir=build
```

---

## 📞 Support

If you encounter issues:
1. Check logs: `mvn spring-boot:run` shows backend logs
2. Browser console: F12 in frontend for JavaScript errors
3. MySQL logs: `mysql -u root -p` and check database
4. Check documentation files in project root

---

## ✨ Next Steps

After successful setup:
1. Read API_DOCUMENTATION.md for all available endpoints
2. Test features with frontend UI
3. Explore dashboard features:
   - User registration/login
   - SOS alert system
   - Location tracking
   - Risk detection
   - AI-powered distress analysis

Happy exploring! 🛡️
