#!/bin/bash
set -e

echo "=== Building The Floor App with Frontend ==="
echo ""

cd "$(dirname "$0")/../backend"

echo "Cleaning previous build..."
./gradlew clean

echo ""
echo "Building application with frontend..."
./gradlew buildWithFrontend

echo ""
echo "=== ✅ BUILD COMPLETE ==="
echo ""
echo "JAR location: backend/build/libs/the-floor-app-backend-0.0.1-SNAPSHOT.jar"
echo "JAR size:     $(ls -lh build/libs/the-floor-app-backend-0.0.1-SNAPSHOT.jar | awk '{print $5}')"
echo ""
echo "To run the application:"
echo "  java -jar backend/build/libs/the-floor-app-backend-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod"
echo ""
echo "Application will be available at: http://localhost:8080"
echo "Have fun!"
echo ""
