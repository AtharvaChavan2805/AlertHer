# Women Safety System - Frontend (React)

This is the frontend for the Women Safety System built with React.

## Project Structure

```
frontend/
├── public/
│   └── index.html
├── src/
│   ├── components/         # React Components (to be created)
│   ├── pages/              # Page Components (to be created)
│   ├── services/           # API Services (to be created)
│   ├── App.js              # Main App Component
│   ├── App.css
│   ├── index.js            # Entry Point
│   └── index.css
├── package.json
└── .gitignore
```

## Tech Stack

- **Framework:** React 18.2.0
- **Package Manager:** npm
- **HTTP Client:** Fetch API (built-in)
- **Styling:** CSS3

## Prerequisites

- Node.js 16+
- npm 8+

## Installation & Setup

1. Install dependencies:
```bash
cd frontend
npm install
```

2. Start development server:
```bash
npm start
```

The app will open at `http://localhost:3000`

3. Build for production:
```bash
npm run build
```

## Features (To be implemented in phases)

- **Phase 9:** SOS button
- **Phase 9:** Live location tracking (every 10 seconds)
- **Phase 9:** Display warning alerts
- **Phase 9:** Input box for distress message
- **Phase 10:** Location tracking implementation
- **Phase 10:** SOS trigger logic
- **Phase 10:** Warning display logic
- **Phase 10:** AI message analysis

## Component Structure (To be created)

- `pages/Login.js` - Login/Registration page
- `pages/Dashboard.js` - Main dashboard
- `components/SOSButton.js` - Emergency SOS button
- `components/LocationTracker.js` - Location tracking component
- `components/AlertDisplay.js` - Warning alert display
- `components/AIChat.js` - AI distress detection interface
- `services/api.js` - API service calls
- `services/location.js` - Geolocation services

## API Integration

Backend API URL: `http://localhost:8080/api`

## Next Steps

After backend PHASE 3 is complete, implement PHASE 9 - React Frontend.
