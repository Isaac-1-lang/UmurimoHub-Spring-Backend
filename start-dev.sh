#!/bin/bash

# UmurimoHub Development Startup Script
# This script starts both backend and frontend servers

echo "🚀 Starting UmurimoHub Development Environment..."
echo ""

# Colors for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Check if backend directory exists
if [ ! -d "src" ]; then
    echo "❌ Backend directory not found. Please run this script from the project root."
    exit 1
fi

# Check if frontend directory exists
if [ ! -d "UmurimoHub-Frontend" ]; then
    echo "❌ Frontend directory not found. Please run this script from the project root."
    exit 1
fi

# Start backend
echo -e "${BLUE}📦 Starting Backend (Spring Boot)...${NC}"
echo "   Port: 8081"
echo "   Swagger: http://localhost:8081/swagger-ui.html"
echo ""

# Start backend in background
./mvnw spring-boot:run > backend.log 2>&1 &
BACKEND_PID=$!
echo "   Backend PID: $BACKEND_PID"

# Wait for backend to start
echo "   Waiting for backend to start..."
sleep 10

# Check if backend is running
if ! curl -s http://localhost:8081/actuator/health > /dev/null; then
    echo -e "${YELLOW}⚠️  Backend may not be ready yet. Check backend.log for details.${NC}"
else
    echo -e "${GREEN}✅ Backend is running!${NC}"
fi

echo ""

# Start frontend
echo -e "${BLUE}🎨 Starting Frontend (React + Vite)...${NC}"
echo "   Port: 8080"
echo "   URL: http://localhost:8080"
echo ""

cd UmurimoHub-Frontend

# Check if node_modules exists
if [ ! -d "node_modules" ]; then
    echo "   Installing dependencies..."
    npm install
fi

# Start frontend in background
npm run dev > ../frontend.log 2>&1 &
FRONTEND_PID=$!
echo "   Frontend PID: $FRONTEND_PID"

cd ..

echo ""
echo -e "${GREEN}✅ Development environment started!${NC}"
echo ""
echo "📝 Quick Start:"
echo "   1. Open http://localhost:8080"
echo "   2. Register as CEO at /register"
echo "   3. Create HR/Worker accounts from CEO dashboard"
echo ""
echo "📚 Documentation:"
echo "   - Integration Guide: INTEGRATION_GUIDE.md"
echo "   - API Docs: http://localhost:8081/swagger-ui.html"
echo ""
echo "📋 Process IDs:"
echo "   Backend:  $BACKEND_PID"
echo "   Frontend: $FRONTEND_PID"
echo ""
echo "🛑 To stop servers:"
echo "   kill $BACKEND_PID $FRONTEND_PID"
echo ""
echo "📄 Logs:"
echo "   Backend:  tail -f backend.log"
echo "   Frontend: tail -f frontend.log"
echo ""

# Save PIDs to file for easy cleanup
echo "$BACKEND_PID" > .dev-pids
echo "$FRONTEND_PID" >> .dev-pids

echo "Press Ctrl+C to stop monitoring (servers will continue running)"
echo "Or run: ./stop-dev.sh to stop all servers"
echo ""

# Monitor logs
tail -f backend.log frontend.log
