# Women Safety System - Backend (Spring Boot)

This is the backend API for the Women Safety System built with Spring Boot.

## Project Structure

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/womensafety/
│   │   │   ├── controller/        # REST API Controllers
│   │   │   ├── service/           # Business Logic
│   │   │   ├── repository/        # Data Access Layer
│   │   │   ├── model/             # JPA Entities
│   │   │   └── WomenSafetyApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
└── .gitignore
```

## Tech Stack

- **Framework:** Spring Boot 3.1.5
- **Build Tool:** Maven
- **Database:** MySQL
- **ORM:** JPA / Hibernate
- **Security:** Spring Security + JWT
- **Java Version:** 17

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+

## Database Setup

1. Create MySQL database:
```sql
CREATE DATABASE women_safety_db;
```

2. Update `application.properties` with your MySQL credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/women_safety_db
spring.datasource.username=root
spring.datasource.password=your_password
```

## Running the Application

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

The server will start on `http://localhost:8080/api`

## API Endpoints (To be implemented in phases)

- **Phase 3:** User registration & login
- **Phase 3:** Emergency contacts CRUD
- **Phase 4:** Location tracking
- **Phase 5:** SOS alert system
- **Phase 6:** Risk detection
- **Phase 7:** Warning system
- **Phase 8:** AI analysis

## Dependencies

- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-boot-starter-security
- mysql-connector-java
- jjwt (JWT)
- lombok
- validation-api

## Next Steps

Proceed to PHASE 2 - Database Entities to create all JPA entities.

## Real SOS Notifications (Twilio)

The SOS flow can now send real SMS notifications to:

- Emergency contact phone numbers saved for the user
- Optional police/station numbers configured via environment variables

### 1. Configure environment variables

In PowerShell before running backend:

```powershell
$env:SOS_NOTIFICATION_ENABLED="true"
$env:SOS_TWILIO_ENABLED="true"
$env:TWILIO_ACCOUNT_SID="ACxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
$env:TWILIO_AUTH_TOKEN="your_twilio_auth_token"
$env:TWILIO_FROM_NUMBER="+1xxxxxxxxxx"
$env:SOS_POLICE_ENABLED="true"
$env:SOS_POLICE_NUMBERS="+91100,+91112"
```

Notes:

- Use E.164 phone number format (for example: +919876543210)
- If a contact number is entered as a 10-digit Indian number, backend auto-normalizes it to +91 format

### 2. Run the backend

```bash
cd backend
mvn clean spring-boot:run
```

### 3. Trigger SOS

Call:

`POST /api/sos/trigger?userId={id}&latitude={lat}&longitude={lon}`

If configuration is valid, SMS notifications are sent immediately.
