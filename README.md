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

### Alternative: Using Gradle Directly

You can also build using Gradle directly:

```bash
cd backend
./gradlew clean buildWithFrontend
```

**Note**: The shell script is recommended as it's more reliable and works consistently across different environments.

### Testing the Build

Run the automated test script to verify everything works:

```bash
./test-build.sh
```

## Available Gradle Tasks

### Frontend Build Tasks
- `./gradlew buildFrontend` - Build frontend (with incremental build support)
- `./gradlew buildFrontendForce` - Force complete frontend rebuild (useful for troubleshooting)
- `./gradlew cleanFrontendDist` - Clean frontend dist directory
- `./gradlew npmInstall` - Install npm dependencies

### Complete Build Tasks
- `./gradlew bootRun` - Run backend only (for development)
- `./gradlew buildWithFrontend` - Build complete application with frontend
- `./gradlew clean buildWithFrontend` - Clean build (recommended for production)

For more details, see [QUICK_REFERENCE.md](QUICK_REFERENCE.md)

## Documentation

- **[QUICK_REFERENCE.md](QUICK_REFERENCE.md)** - Quick command reference
- **[FRONTEND_BACKEND_INTEGRATION.md](FRONTEND_BACKEND_INTEGRATION.md)** - Complete integration guide
- **[BUILDFRONTEND_TROUBLESHOOTING.md](BUILDFRONTEND_TROUBLESHOOTING.md)** - Troubleshooting guide
- **[DEVELOPMENT.md](DEVELOPMENT.md)** - Development guidelines

## Troubleshooting

If you experience issues with the frontend build:

```bash
cd backend
./gradlew --stop                    # Stop Gradle daemon
./gradlew buildFrontendForce        # Force rebuild
./gradlew clean buildWithFrontend   # Complete clean build
```

For more detailed troubleshooting, see [BUILDFRONTEND_TROUBLESHOOTING.md](BUILDFRONTEND_TROUBLESHOOTING.md)
