[![CI Pipeline](https://github.com/puscik21/the-floor-app/actions/workflows/ci.yml/badge.svg)](https://github.com/puscik21/the-floor-app/actions/workflows/ci.yml)

# The Floor App

A full-stack application using Spring Boot with Java 25 as a backend and React + TypeScript (built with Vite) as a frontend.

## Tech Stack

- **Backend**: Spring Boot 4.0.2, Java 25
- **Frontend**: React 19, TypeScript, Vite 7, Material-UI
- **Database**: H2 (in-memory)

## Development Setup

### Prerequisites
- Java 25
- Node.js and npm
- Gradle (or use included gradlew)

### Running in Development Mode (Recommended)

In development, run frontend and backend separately for the best experience with hot reload:

#### 1. Start the Backend
```bash
cd backend
./gradlew bootRun --args='--spring.profiles.active=dev'
```
Backend will be available at: http://localhost:8080

#### 2. Start the Frontend (in a separate terminal)
```bash
cd frontend
npm install
npm run dev
```
Frontend will be available at: http://localhost:5173

The frontend will automatically proxy API calls to the backend at port 8080.

### Running Tests

```bash
cd backend
./gradlew test
```

## Production Build

### Recommended: Using Build Script (Most Reliable)

The easiest and most reliable way to build for production:

```bash
./build-with-frontend.sh
```

This will:
1. Clean previous builds
2. Build the frontend with Vite
3. Build the backend with Spring Boot
4. Create a single executable JAR with frontend included

The resulting JAR will be in `backend/build/libs/` and can be run with:

```bash
java -jar backend/build/libs/the-floor-app-backend-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

Everything will be served from http://localhost:8080
