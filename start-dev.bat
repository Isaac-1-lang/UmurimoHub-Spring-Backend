@echo off
REM UmurimoHub Development Startup Script for Windows
REM This script starts both backend and frontend servers

echo.
echo Starting UmurimoHub Development Environment...
echo.

REM Check if backend directory exists
if not exist "src" (
    echo Backend directory not found. Please run this script from the project root.
    exit /b 1
)

REM Check if frontend directory exists
if not exist "UmurimoHub-Frontend" (
    echo Frontend directory not found. Please run this script from the project root.
    exit /b 1
)

REM Start backend
echo Starting Backend (Spring Boot)...
echo    Port: 8081
echo    Swagger: http://localhost:8081/swagger-ui.html
echo.

start "UmurimoHub Backend" cmd /c "mvnw.cmd spring-boot:run > backend.log 2>&1"

echo    Waiting for backend to start...
timeout /t 10 /nobreak > nul

echo    Backend started!
echo.

REM Start frontend
echo Starting Frontend (React + Vite)...
echo    Port: 8080
echo    URL: http://localhost:8080
echo.

cd UmurimoHub-Frontend

REM Check if node_modules exists
if not exist "node_modules" (
    echo    Installing dependencies...
    call npm install
)

start "UmurimoHub Frontend" cmd /c "npm run dev > ..\frontend.log 2>&1"

cd ..

echo.
echo Development environment started!
echo.
echo Quick Start:
echo    1. Open http://localhost:8080
echo    2. Register as CEO at /register
echo    3. Create HR/Worker accounts from CEO dashboard
echo.
echo Documentation:
echo    - Integration Guide: INTEGRATION_GUIDE.md
echo    - API Docs: http://localhost:8081/swagger-ui.html
echo.
echo Logs:
echo    Backend:  backend.log
echo    Frontend: frontend.log
echo.
echo To stop servers, close the terminal windows or press Ctrl+C in each window.
echo.

pause
