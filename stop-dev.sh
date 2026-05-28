#!/bin/bash

# UmurimoHub Development Stop Script
# This script stops both backend and frontend servers

echo "🛑 Stopping UmurimoHub Development Environment..."
echo ""

# Check if PID file exists
if [ ! -f ".dev-pids" ]; then
    echo "❌ No running servers found (.dev-pids file not found)"
    echo "   Servers may have been started manually."
    echo ""
    echo "To stop manually:"
    echo "   - Find processes: ps aux | grep 'spring-boot\\|vite'"
    echo "   - Kill processes: kill <PID>"
    exit 1
fi

# Read PIDs
BACKEND_PID=$(sed -n '1p' .dev-pids)
FRONTEND_PID=$(sed -n '2p' .dev-pids)

# Stop backend
if [ -n "$BACKEND_PID" ]; then
    if ps -p $BACKEND_PID > /dev/null; then
        echo "🔴 Stopping Backend (PID: $BACKEND_PID)..."
        kill $BACKEND_PID
        echo "   ✅ Backend stopped"
    else
        echo "⚠️  Backend process not found (PID: $BACKEND_PID)"
    fi
fi

# Stop frontend
if [ -n "$FRONTEND_PID" ]; then
    if ps -p $FRONTEND_PID > /dev/null; then
        echo "🔴 Stopping Frontend (PID: $FRONTEND_PID)..."
        kill $FRONTEND_PID
        echo "   ✅ Frontend stopped"
    else
        echo "⚠️  Frontend process not found (PID: $FRONTEND_PID)"
    fi
fi

# Clean up
rm -f .dev-pids
echo ""
echo "✅ Development environment stopped!"
echo ""
echo "📄 Logs are still available:"
echo "   Backend:  backend.log"
echo "   Frontend: frontend.log"
