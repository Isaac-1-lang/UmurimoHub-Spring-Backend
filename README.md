# UmurimoHub - Workforce Management System

A comprehensive full-stack workforce management system with a Spring Boot REST API backend and React TypeScript frontend, featuring JWT authentication, role-based access control, attendance tracking, salary deductions, disciplinary actions, and operational dashboards.

## 🚀 Features

### Authentication & User Management
- **CEO Registration**: Public registration for CEO accounts
- **User Creation**: CEO can create HR and Worker accounts
- **Email Notifications**: Credentials sent to new users via email
- **JWT Authentication**: Secure token-based authentication
- **Role-Based Access**: CEO, HR, and Worker roles with specific permissions

### Core Functionality
- ✅ JWT-based authentication with secure token management
- ✅ CEO self-registration and user management
- ✅ Worker management (CRUD operations)
- ✅ Attendance tracking and reporting
- ✅ Salary deduction management
- ✅ Disciplinary action tracking
- ✅ Dashboard statistics for CEO and HR
- ✅ Spring Boot Actuator for monitoring
- ✅ Complete Swagger/OpenAPI documentation
- ✅ Email service for credential delivery
- ✅ Modern React frontend with TypeScript
- ✅ Responsive UI with Tailwind CSS
- ✅ H2 in-memory database (development)
- ✅ MySQL support (production)

## 🛠️ Technology Stack

### Backend
- **Java 17**
- **Spring Boot 3.2.5**
- **Spring Security** (JWT Authentication)
- **Spring Data JPA** (Hibernate)
- **JJWT** (JWT token generation and validation)
- **H2 Database** (Development)
- **MySQL** (Production)
- **SpringDoc OpenAPI** (Swagger UI)
- **Spring Boot Actuator** (Monitoring)
- **Maven** (Build tool)

### Frontend
- **React 19**
- **TypeScript**
- **TanStack Router** (Routing)
- **TanStack Query** (Data fetching)
- **Tailwind CSS** (Styling)
- **Radix UI** (Component library)
- **Vite** (Build tool)
- **Bun/npm** (Package manager)

## 📋 Prerequisites

### Backend
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+ (for production)

### Frontend
- Node.js 18+ or Bun
- npm, yarn, or bun

## 🏃 Quick Start

### Option 1: Automated Startup (Recommended)

#### Linux/Mac
```bash
chmod +x start-dev.sh
./start-dev.sh
```

#### Windows
```bash
start-dev.bat
```

This will start both backend (port 8081) and frontend (port 8080) automatically.

### Option 2: Manual Startup

#### Start Backend
```bash
./mvnw spring-boot:run
```

Or on Windows:
```bash
mvnw.cmd spring-boot:run
```

#### Start Frontend
```bash
cd UmurimoHub-Frontend
npm install
npm run dev
```

### 3. Access the application

- **Frontend**: http://localhost:8080
- **Backend API**: http://localhost:8081/api
- **Swagger UI**: http://localhost:8081/swagger-ui.html
- **H2 Console**: http://localhost:8081/h2-console
- **Actuator**: http://localhost:8081/actuator

## 🔐 Authentication Flow

### New Authentication System

1. **CEO Registration** (Public)
   - Anyone can register as a CEO at `/register`
   - Provides: username, password, full name, email, phone number
   - Automatically logged in after registration

2. **CEO Creates Users**
   - CEO navigates to "User Management" in dashboard
   - Creates HR or Worker accounts
   - System generates credentials and sends via email

3. **HR/Worker Login**
   - Users receive credentials via email
   - Login at `/login` with username and password
   - Receive JWT token for subsequent requests

4. **JWT Token Usage**
   - Token stored in localStorage
   - Automatically included in all API requests
   - Token format: `Bearer <token>`

## 👥 User Roles & Permissions

### CEO
- ✅ Register publicly
- ✅ Create HR and Worker accounts
- ✅ View all operational data and analytics
- ✅ Access executive dashboard
- ✅ Monitor HR activities
- ❌ Cannot create other CEO accounts

### HR
- ✅ Created by CEO
- ✅ Manage workers (CRUD)
- ✅ Track attendance
- ✅ Manage salary deductions
- ✅ Handle disciplinary actions
- ✅ Generate reports
- ❌ Cannot create users

### Worker
- ✅ Created by CEO
- ✅ View personal attendance
- ✅ View salary deductions
- ✅ View disciplinary records
- ✅ Update profile
- ❌ Cannot access other workers' data

## 📚 API Endpoints

### Authentication
- `POST /api/auth/register` - Register new CEO account
- `POST /api/auth/login` - User login (returns JWT token)
- `GET /api/auth/me` - Get current user details
- `POST /api/auth/logout` - User logout

### User Management (CEO only)
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `POST /api/users` - Create HR or Worker account

### Workers (HR only)
- `GET /api/workers` - Get all workers
- `GET /api/workers/{id}` - Get worker by ID
- `POST /api/workers` - Create worker
- `PUT /api/workers/{id}` - Update worker
- `DELETE /api/workers/{id}` - Delete worker

### Attendance (HR only)
- `GET /api/attendance` - Get all attendance records
- `POST /api/attendance` - Record attendance
- `GET /api/attendance/worker/{workerId}` - Get worker's attendance

### Salary Deductions (HR only)
- `GET /api/salary-deductions` - Get all deductions
- `POST /api/salary-deductions` - Create deduction
- `GET /api/salary-deductions/worker/{workerId}` - Get worker's deductions

### Disciplinary Actions (HR only)
- `GET /api/disciplinary-actions` - Get all actions
- `POST /api/disciplinary-actions` - Create action
- `GET /api/disciplinary-actions/worker/{workerId}` - Get worker's actions

### Dashboard
- `GET /api/dashboard/ceo-stats` - CEO dashboard statistics
- `GET /api/dashboard/hr-stats` - HR dashboard statistics

## 🎯 Getting Started Guide

### Step 1: Start the Servers
```bash
# Backend
./mvnw spring-boot:run

# Frontend (in another terminal)
cd UmurimoHub-Frontend
npm run dev
```

### Step 2: Register as CEO
1. Open http://localhost:8080/register
2. Fill in the registration form
3. Click "Create CEO Account"
4. You'll be automatically logged in

### Step 3: Create HR Account
1. Navigate to "User Management" in the CEO sidebar
2. Click "Create User"
3. Fill in the form with HR details
4. Select "HR Manager" as the role
5. Click "Create User"
6. Check backend console for email notification with credentials

### Step 4: Login as HR
1. Logout from CEO account
2. Login with HR credentials from the email
3. Access HR dashboard and manage workers

### Step 5: Create Worker Account
1. As CEO, create a Worker account
2. Worker can login and view personal records

## 📖 Detailed Documentation

For comprehensive integration and deployment instructions, see:
- **[INTEGRATION_GUIDE.md](INTEGRATION_GUIDE.md)** - Complete integration guide
- **[Swagger UI](http://localhost:8081/swagger-ui.html)** - Interactive API documentation

## 🔧 Configuration

### Backend Configuration

Edit `src/main/resources/application.properties`:

```properties
# Server
server.port=8081

# Database (H2 for development)
spring.datasource.url=jdbc:h2:mem:umurimodb
spring.h2.console.enabled=true

# JWT
jwt.secret=your-secret-key-here
jwt.expiration=86400000

# Email (for credential notifications)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
```

### Frontend Configuration

Edit `UmurimoHub-Frontend/.env`:

```env
VITE_API_URL=http://localhost:8081/api
```

## 📧 Email Notification System

Currently, the email system is **simulated** and prints credentials to the backend console.

### Console Output Example
```
============================================================
EMAIL NOTIFICATION - New Account Created
============================================================
To: sarah@company.com
Subject: Your UmurimoHub Account Credentials

Dear Sarah Mitchell,

Your account has been created successfully!

Login Credentials:
  Username: sarahmitchell
  Password: hr123456
  Role: HR

Login URL: http://localhost:8081/api/auth/login

Please change your password after first login.

Best regards,
UmurimoHub Team
============================================================
```

### Integrating Real Email Service

To integrate a real email service, update `UserService.java`:
- SendGrid
- AWS SES
- Mailgun
- SMTP server

See `INTEGRATION_GUIDE.md` for detailed instructions.

## 🏗️ Project Structure

```
umurimoHub/
├── src/main/java/              # Backend source code
│   └── ac/cursor/umurimohub/
│       ├── config/             # Configuration classes
│       ├── controller/         # REST controllers
│       ├── dto/                # Data Transfer Objects
│       ├── exception/          # Exception handlers
│       ├── model/              # JPA entities
│       ├── repository/         # JPA repositories
│       ├── security/           # Security & JWT
│       └── service/            # Business logic
├── UmurimoHub-Frontend/        # Frontend source code
│   ├── src/
│   │   ├── components/         # React components
│   │   ├── lib/                # Utilities & API client
│   │   ├── routes/             # Page routes
│   │   │   ├── _app/ceo/       # CEO pages
│   │   │   ├── _app/hr/        # HR pages
│   │   │   └── _app/worker/    # Worker pages
│   │   └── styles.css          # Global styles
│   └── package.json
├── INTEGRATION_GUIDE.md        # Detailed integration guide
├── start-dev.sh                # Linux/Mac startup script
├── start-dev.bat               # Windows startup script
├── stop-dev.sh                 # Stop script
└── README.md                   # This file
```

## 🔒 Security

- JWT (JSON Web Token) authentication
- Role-based access control (CEO, HR, WORKER)
- Password encryption with BCrypt
- Stateless session management
- Bearer token authorization
- CORS configuration for frontend
- CSRF protection disabled for REST API
- Method-level security with `@PreAuthorize`

## 🧪 Testing

### Using Swagger UI
1. Open http://localhost:8081/swagger-ui.html
2. Register a CEO account
3. Copy the JWT token from the response
4. Click "Authorize" button
5. Enter: `Bearer <your-token>`
6. Test all endpoints

### Using Postman
Import `UmurimoHub-API.postman_collection.json` for pre-configured requests.

## 🚀 Deployment

### Backend
```bash
./mvnw clean package
java -jar target/umurimoHub-0.0.1-SNAPSHOT.jar
```

### Frontend
```bash
cd UmurimoHub-Frontend
npm run build
# Deploy dist/ folder to hosting service
```

See `INTEGRATION_GUIDE.md` for production deployment instructions.

## 🛑 Stopping the Servers

### Linux/Mac
```bash
./stop-dev.sh
```

### Windows
Close the terminal windows or press Ctrl+C in each window.

## 📝 License

MIT License

## 👥 Support

For support, email support@umurimohub.ac

---

**Built with ❤️ using Spring Boot & React**

## 🚀 Features

### Role-Based Access Control
- **CEO**: View operational summaries and all workforce data
- **HR**: Full CRUD operations on workers, attendance, deductions, and disciplinary actions
- **WORKER**: View personal attendance records, salary deductions, and disciplinary actions

### Core Functionality
- ✅ JWT-based authentication with OTP email verification
- ✅ User registration with email verification
- ✅ Worker management (CRUD operations)
- ✅ Attendance tracking and reporting
- ✅ Salary deduction management
- ✅ Disciplinary action tracking
- ✅ Dashboard statistics for CEO and HR
- ✅ Spring Boot Actuator for monitoring
- ✅ Complete Swagger/OpenAPI documentation
- ✅ Email service for OTP delivery
- ✅ H2 in-memory database (development)
- ✅ MySQL support (production)

## 🛠️ Technology Stack

- **Java 17**
- **Spring Boot 3.2.5**
- **Spring Security** (JWT Authentication with OTP)
- **Spring Data JPA** (Hibernate)
- **Spring Mail** (Email OTP delivery)
- **JJWT** (JWT token generation and validation)
- **H2 Database** (Development)
- **MySQL** (Production)
- **SpringDoc OpenAPI** (Swagger UI)
- **Spring Boot Actuator** (Monitoring)
- **Maven** (Build tool)

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+ (for production)

## 🏃 Getting Started

### 1. Clone the repository
```bash
git clone <repository-url>
cd umurimoHub
```

### 2. Run the application
```bash
./mvnw spring-boot:run
```

Or on Windows:
```bash
mvnw.cmd spring-boot:run
```

### 3. Access the application

- **Swagger UI**: http://localhost:8081/swagger-ui.html
- **API Docs**: http://localhost:8081/api-docs
- **H2 Console**: http://localhost:8081/h2-console
- **Actuator**: http://localhost:8081/actuator

## 🔐 Authentication Flow

The application uses JWT authentication with email OTP verification:

### 1. Register (CEO only)
```bash
POST /api/auth/register
{
  "username": "ceo_user",
  "password": "password123",
  "fullName": "Full Name",
  "email": "example@example.com",
  "phoneNumber": "+250788000001"
}
```
**Response**: OTP sent to email

### 2. Login (All users)
```bash
POST /api/auth/login
{
  "username": "ceo_user",
  "password": "password123"
}
```
**Response**: OTP sent to email

### 3. Verify OTP
```bash
POST /api/auth/verify
{
  "email": "example@example.com",
  "otpCode": "123456"
}
```
**Response**: JWT token + user details
```json
{
  "success": true,
  "message": "Email verified successfully. You are now logged in.",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "type": "Bearer",
    "id": 1,
    "username": "ceo_user",
    "email": "example@example.com",
    "role": "CEO",
    "fullName": "Full Name",
    "emailVerified": true
  }
}
```

### 4. Use JWT Token
Include the token in all subsequent requests:
```bash
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

## 🔐 Demo Accounts

The application comes with pre-configured demo accounts:

| Role   | Username | Password  | Description                    |
|--------|----------|-----------|--------------------------------|
| CEO    | ceo      | ceo123    | View all operational data      |
| HR     | hr       | hr123     | Manage workers and operations  |
| Worker | worker1  | worker123 | View personal records          |
| Worker | worker2  | worker123 | View personal records          |

## 📚 API Endpoints

### Authentication
- `POST /api/auth/register` - Register new CEO user (sends OTP)
- `POST /api/auth/login` - User login (sends OTP)
- `POST /api/auth/verify` - Verify OTP and get JWT token
- `GET /api/auth/me` - Get current user (requires JWT)
- `POST /api/auth/logout` - User logout (requires JWT)

### Users (HR, CEO)
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `POST /api/users` - Create new user (HR only)

### Workers
- `GET /api/workers` - Get all workers (HR, CEO)
- `GET /api/workers/{id}` - Get worker by ID (HR, CEO)
- `POST /api/workers` - Create worker (HR only)
- `PUT /api/workers/{id}` - Update worker (HR only)
- `GET /api/workers/my-profile` - Get my profile (Worker)

### Attendance
- `GET /api/attendance` - Get all attendance (HR, CEO)
- `GET /api/attendance/worker/{workerId}` - Get worker attendance (HR, CEO)
- `GET /api/attendance/date-range?startDate=&endDate=` - Get by date range (HR, CEO)
- `POST /api/attendance` - Record attendance (HR only)
- `GET /api/attendance/my-attendance` - Get my attendance (Worker)

### Salary Deductions
- `GET /api/salary-deductions` - Get all deductions (HR, CEO)
- `GET /api/salary-deductions/worker/{workerId}` - Get worker deductions (HR, CEO)
- `POST /api/salary-deductions` - Create deduction (HR only)
- `GET /api/salary-deductions/my-deductions` - Get my deductions (Worker)

### Disciplinary Actions
- `GET /api/disciplinary-actions` - Get all actions (HR, CEO)
- `GET /api/disciplinary-actions/worker/{workerId}` - Get worker actions (HR, CEO)
- `POST /api/disciplinary-actions` - Create action (HR only)
- `GET /api/disciplinary-actions/my-actions` - Get my actions (Worker)

### Dashboard
- `GET /api/dashboard/ceo-stats` - CEO dashboard statistics
- `GET /api/dashboard/hr-stats` - HR dashboard statistics

## 🔧 Configuration

### Database Configuration

#### Development (H2)
The application uses H2 in-memory database by default. Configuration in `application.properties`:

```properties
spring.datasource.url=jdbc:h2:mem:umurimodb
spring.h2.console.enabled=true
```

#### Production (MySQL)
To use MySQL, update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/umurimodb
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### Actuator Endpoints

Available at `/actuator`:
- `/actuator/health` - Application health status
- `/actuator/info` - Application information
- `/actuator/metrics` - Application metrics
- `/actuator/env` - Environment properties
- `/actuator/beans` - Spring beans
- `/actuator/mappings` - Request mappings

## 📖 API Documentation

Complete interactive API documentation is available via Swagger UI at:
```
http://localhost:8081/swagger-ui.html
```

Features:
- Try out all endpoints directly from the browser
- View request/response schemas
- See authentication requirements
- Test with demo accounts

## 🧪 Testing with cURL

### 1. Register New CEO
```bash
curl -X POST http://localhost:8081/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "ceo_example",
    "password": "password123",
    "fullName": "Full Name",
    "email": "example@example.com",
    "phoneNumber": "+250788000001"
  }'
```
**Response**: Check email for OTP code

### 2. Login
```bash
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "ceo_example",
    "password": "password123"
  }'
```
**Response**: Check email for OTP code

### 3. Verify OTP and Get JWT Token
```bash
curl -X POST http://localhost:8081/api/auth/verify \
  -H "Content-Type: application/json" \
  -d '{
    "email": "example@example.com",
    "otpCode": "123456"
  }'
```
**Response**: JWT token in response

### 4. Use JWT Token for Protected Endpoints
```bash
# Get All Workers (with JWT)
curl -X GET http://localhost:8081/api/workers \
  -H "Authorization: Bearer YOUR_JWT_TOKEN_HERE"
```

### Create Worker
```bash
curl -X POST http://localhost:8081/api/workers \
  -H "Authorization: Bearer YOUR_JWT_TOKEN_HERE" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "newworker",
    "password": "password123",
    "fullName": "New Worker",
    "email": "newworker@example.com",
    "phoneNumber": "+250788000005",
    "employeeId": "EMP003",
    "department": "Finance",
    "position": "Accountant",
    "hireDate": "2024-01-15",
    "baseSalary": 450000
  }'
```

### Record Attendance
```bash
curl -X POST http://localhost:8081/api/attendance \
  -H "Authorization: Bearer YOUR_JWT_TOKEN_HERE" \
  -H "Content-Type: application/json" \
  -d '{
    "workerId": 1,
    "date": "2024-05-14",
    "checkIn": "08:00:00",
    "checkOut": "17:00:00",
    "status": "PRESENT",
    "notes": "On time"
  }'
```

## 🏗️ Project Structure

```
src/main/java/ac/cursor/umurimohub/
├── config/              # Configuration classes
│   ├── DataInitializer.java
│   └── OpenApiConfig.java
├── controller/          # REST API controllers
│   ├── AuthController.java
│   ├── WorkerRestController.java
│   ├── AttendanceRestController.java
│   ├── SalaryDeductionRestController.java
│   ├── DisciplinaryActionRestController.java
│   ├── DashboardRestController.java
│   └── UserRestController.java
├── dto/                 # Data Transfer Objects
│   ├── ApiResponse.java
│   ├── LoginRequest.java
│   ├── RegisterRequest.java
│   ├── VerifyOtpRequest.java
│   ├── AuthResponse.java
│   ├── WorkerRequest.java
│   ├── AttendanceRequest.java
│   ├── SalaryDeductionRequest.java
│   └── DisciplinaryActionRequest.java
├── exception/           # Exception handlers
│   └── GlobalExceptionHandler.java
├── model/               # JPA entities
│   ├── User.java
│   ├── Worker.java
│   ├── Attendance.java
│   ├── SalaryDeduction.java
│   ├── DisciplinaryAction.java
│   └── Otp.java
├── repository/          # JPA repositories
│   ├── UserRepository.java
│   ├── WorkerRepository.java
│   ├── AttendanceRepository.java
│   ├── SalaryDeductionRepository.java
│   ├── DisciplinaryActionRepository.java
│   └── OtpRepository.java
├── security/            # Security configuration
│   ├── SecurityConfig.java
│   ├── CustomUserDetailsService.java
│   ├── JwtUtil.java
│   └── JwtAuthenticationFilter.java
└── service/             # Business logic
    ├── UserService.java
    ├── WorkerService.java
    ├── AttendanceService.java
    ├── SalaryDeductionService.java
    ├── DisciplinaryActionService.java
    ├── OtpService.java
    └── EmailService.java
```

## 🔒 Security

- JWT (JSON Web Token) authentication
- Email OTP verification (10-minute expiry)
- Role-based access control (CEO, HR, WORKER)
- Password encryption with BCrypt
- Stateless session management
- Bearer token authorization
- CSRF protection disabled for REST API
- Method-level security with `@PreAuthorize`

## � Email Configuration

The application uses Gmail SMTP for sending OTP codes. Configure in `application.properties`:

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

**Note**: For Gmail, you need to use an [App Password](https://support.google.com/accounts/answer/185833) instead of your regular password.

## 📊 Database Schema

### Users
- id, username, password, fullName, email, phoneNumber, role, active, emailVerified, createdAt, updatedAt

### OTPs
- id, email, otpCode, expiryTime, verified, createdAt

### Workers
- id, userId, employeeId, department, position, hireDate, baseSalary, status

### Attendance
- id, workerId, date, checkIn, checkOut, status, notes

### Salary Deductions
- id, workerId, deductionDate, amount, type, reason, approvedBy

### Disciplinary Actions
- id, workerId, actionDate, type, description, severity, issuedBy

## 🚀 Deployment

### Build JAR
```bash
./mvnw clean package
```

### Run JAR
```bash
java -jar target/umurimoHub-0.0.1-SNAPSHOT.jar
```

### Docker (Optional)
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/umurimoHub-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

## 📝 License

MIT License

## 👥 Support

For support, email support@umurimohub.ac

---

**Built with ❤️ using Spring Boot**
