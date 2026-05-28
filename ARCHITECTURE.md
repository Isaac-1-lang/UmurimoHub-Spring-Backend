# UmurimoHub System Architecture

## System Overview

```
┌─────────────────────────────────────────────────────────────────┐
│                         UmurimoHub System                        │
│                    Workforce Management Platform                 │
└─────────────────────────────────────────────────────────────────┘

┌──────────────────────┐                    ┌──────────────────────┐
│                      │                    │                      │
│   React Frontend     │◄──────────────────►│  Spring Boot API     │
│   (Port 8080)        │    REST + JWT      │   (Port 8081)        │
│                      │                    │                      │
│  - TanStack Router   │                    │  - Spring Security   │
│  - TypeScript        │                    │  - JWT Auth          │
│  - Tailwind CSS      │                    │  - JPA/Hibernate     │
│  - Radix UI          │                    │  - Spring Mail       │
│                      │                    │                      │
└──────────────────────┘                    └──────────┬───────────┘
                                                       │
                                                       │
                                            ┌──────────▼───────────┐
                                            │                      │
                                            │   H2 Database        │
                                            │   (Development)      │
                                            │                      │
                                            │   MySQL              │
                                            │   (Production)       │
                                            │                      │
                                            └──────────────────────┘
```

## Authentication Flow

```
┌─────────────┐                                    ┌─────────────┐
│             │  1. Register (CEO)                 │             │
│   Browser   ├───────────────────────────────────►│   Backend   │
│             │    POST /api/auth/register         │             │
│             │                                    │             │
│             │◄───────────────────────────────────┤             │
│             │  2. JWT Token + User Data          │             │
│             │                                    │             │
│             │  3. Store Token in localStorage    │             │
│             │                                    │             │
│             │  4. All API Requests               │             │
│             ├───────────────────────────────────►│             │
│             │    Authorization: Bearer <token>   │             │
│             │                                    │             │
│             │◄───────────────────────────────────┤             │
│             │  5. Protected Resource             │             │
└─────────────┘                                    └─────────────┘
```

## User Creation Flow

```
┌─────────┐         ┌──────────┐         ┌──────────┐         ┌─────────┐
│   CEO   │         │ Frontend │         │ Backend  │         │  Email  │
└────┬────┘         └─────┬────┘         └─────┬────┘         └────┬────┘
     │                    │                    │                   │
     │ 1. Create User     │                    │                   │
     ├───────────────────►│                    │                   │
     │                    │                    │                   │
     │                    │ 2. POST /api/users │                   │
     │                    ├───────────────────►│                   │
     │                    │                    │                   │
     │                    │                    │ 3. Generate       │
     │                    │                    │    Credentials    │
     │                    │                    │                   │
     │                    │                    │ 4. Send Email     │
     │                    │                    ├──────────────────►│
     │                    │                    │    (Console)      │
     │                    │                    │                   │
     │                    │ 5. User Created    │                   │
     │                    │◄───────────────────┤                   │
     │                    │                    │                   │
     │ 6. Success Message │                    │                   │
     │◄───────────────────┤                    │                   │
     │                    │                    │                   │
```

## Role-Based Access Control

```
┌──────────────────────────────────────────────────────────────────┐
│                         User Roles                                │
└──────────────────────────────────────────────────────────────────┘

┌─────────────────┐
│      CEO        │
├─────────────────┤
│ Permissions:    │
│ ✓ Register      │
│ ✓ Create Users  │
│ ✓ View All Data │
│ ✓ Analytics     │
│ ✓ Monitoring    │
│ ✗ Create CEOs   │
└─────────────────┘

┌─────────────────┐
│       HR        │
├─────────────────┤
│ Permissions:    │
│ ✓ Manage Workers│
│ ✓ Attendance    │
│ ✓ Deductions    │
│ ✓ Disciplinary  │
│ ✓ Reports       │
│ ✗ Create Users  │
└─────────────────┘

┌─────────────────┐
│     Worker      │
├─────────────────┤
│ Permissions:    │
│ ✓ View Own Data │
│ ✓ Attendance    │
│ ✓ Deductions    │
│ ✓ Disciplinary  │
│ ✓ Profile       │
│ ✗ Manage Others │
└─────────────────┘
```

## API Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                      Spring Boot Layers                          │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                        Controllers                               │
│  AuthController │ UserController │ WorkerController │ etc.       │
└────────────────────────────┬────────────────────────────────────┘
                             │
┌────────────────────────────▼────────────────────────────────────┐
│                         Services                                 │
│  UserService │ WorkerService │ AttendanceService │ etc.          │
└────────────────────────────┬────────────────────────────────────┘
                             │
┌────────────────────────────▼────────────────────────────────────┐
│                       Repositories                               │
│  UserRepository │ WorkerRepository │ AttendanceRepository │ etc. │
└────────────────────────────┬────────────────────────────────────┘
                             │
┌────────────────────────────▼────────────────────────────────────┐
│                         Database                                 │
│  Users │ Workers │ Attendance │ Deductions │ Disciplinary        │
└─────────────────────────────────────────────────────────────────┘
```

## Security Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                      Security Layers                             │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                   JWT Authentication Filter                      │
│  - Extract token from Authorization header                       │
│  - Validate token signature and expiration                       │
│  - Load user details and authorities                             │
└────────────────────────────┬────────────────────────────────────┘
                             │
┌────────────────────────────▼────────────────────────────────────┐
│                    Spring Security                               │
│  - Role-based access control (@PreAuthorize)                     │
│  - Method-level security                                         │
│  - CORS configuration                                            │
└────────────────────────────┬────────────────────────────────────┘
                             │
┌────────────────────────────▼────────────────────────────────────┐
│                      Controllers                                 │
│  - Public: /api/auth/register, /api/auth/login                  │
│  - CEO: /api/users, /api/dashboard/ceo-stats                    │
│  - HR: /api/workers, /api/attendance, etc.                      │
│  - Worker: /api/workers/my-profile, etc.                        │
└─────────────────────────────────────────────────────────────────┘
```

## Frontend Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                    React Application                             │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                      TanStack Router                             │
│  Public Routes: / │ /login │ /register                          │
│  CEO Routes: /ceo/dashboard │ /ceo/users │ /ceo/analytics       │
│  HR Routes: /hr/dashboard │ /hr/workers │ /hr/attendance        │
│  Worker Routes: /worker/dashboard │ /worker/attendance           │
└────────────────────────────┬────────────────────────────────────┘
                             │
┌────────────────────────────▼────────────────────────────────────┐
│                      Auth Context                                │
│  - User state management                                         │
│  - Login/Register/Logout functions                               │
│  - Token storage in localStorage                                 │
└────────────────────────────┬────────────────────────────────────┘
                             │
┌────────────────────────────▼────────────────────────────────────┐
│                       API Client                                 │
│  - Centralized API calls                                         │
│  - Automatic token injection                                     │
│  - Error handling                                                │
│  - Type-safe interfaces                                          │
└────────────────────────────┬────────────────────────────────────┘
                             │
┌────────────────────────────▼────────────────────────────────────┐
│                      Backend API                                 │
│  http://localhost:8081/api                                       │
└─────────────────────────────────────────────────────────────────┘
```

## Data Flow Example: Creating a Worker

```
1. CEO logs in
   ├─► Frontend: POST /api/auth/login
   ├─► Backend: Validate credentials
   ├─► Backend: Generate JWT token
   └─► Frontend: Store token in localStorage

2. CEO navigates to User Management
   ├─► Frontend: GET /api/users (with JWT)
   ├─► Backend: Validate token
   ├─► Backend: Check CEO role
   ├─► Backend: Fetch users from database
   └─► Frontend: Display user list

3. CEO creates Worker account
   ├─► Frontend: POST /api/users (with JWT + user data)
   ├─► Backend: Validate token
   ├─► Backend: Check CEO role
   ├─► Backend: Validate user data
   ├─► Backend: Check for duplicates
   ├─► Backend: Hash password
   ├─► Backend: Save to database
   ├─► Backend: Send email notification (console)
   └─► Frontend: Show success message

4. Worker receives credentials
   ├─► Email: Username and password
   └─► Worker can now login

5. Worker logs in
   ├─► Frontend: POST /api/auth/login
   ├─► Backend: Validate credentials
   ├─► Backend: Generate JWT token
   └─► Frontend: Redirect to /worker/dashboard
```

## Technology Stack Details

### Backend
```
Spring Boot 3.2.5
├── Spring Security (Authentication & Authorization)
├── Spring Data JPA (Database ORM)
├── Spring Web (REST API)
├── Spring Mail (Email notifications)
├── JJWT (JWT token handling)
├── H2 Database (Development)
├── MySQL Connector (Production)
├── SpringDoc OpenAPI (API documentation)
└── Spring Boot Actuator (Monitoring)
```

### Frontend
```
React 19
├── TypeScript (Type safety)
├── TanStack Router (Routing)
├── TanStack Query (Data fetching)
├── Tailwind CSS (Styling)
├── Radix UI (Component library)
├── Vite (Build tool)
├── date-fns (Date formatting)
├── Sonner (Toast notifications)
└── Lucide React (Icons)
```

## Deployment Architecture

### Development
```
┌──────────────┐         ┌──────────────┐
│   Frontend   │         │   Backend    │
│ localhost:   │◄───────►│ localhost:   │
│   8080       │         │   8081       │
└──────────────┘         └──────┬───────┘
                                │
                         ┌──────▼───────┐
                         │  H2 Database │
                         │  (In-Memory) │
                         └──────────────┘
```

### Production
```
┌──────────────┐         ┌──────────────┐         ┌──────────────┐
│   CDN/Host   │         │   App Server │         │   Database   │
│  (Frontend)  │◄───────►│   (Backend)  │◄───────►│    MySQL     │
│  Vercel/     │  HTTPS  │   AWS/       │  JDBC   │   AWS RDS/   │
│  Netlify     │         │   Heroku     │         │   Cloud SQL  │
└──────────────┘         └──────────────┘         └──────────────┘
```

## Security Measures

### Implemented
- ✅ JWT token authentication
- ✅ Password hashing (BCrypt)
- ✅ Role-based access control
- ✅ CORS configuration
- ✅ Stateless sessions
- ✅ Method-level security

### Recommended for Production
- 🔒 HTTPS enforcement
- 🔒 Rate limiting
- 🔒 Account lockout
- 🔒 Password complexity rules
- 🔒 Token refresh mechanism
- 🔒 Security headers (HSTS, CSP)
- 🔒 Input validation & sanitization
- 🔒 SQL injection prevention (JPA)
- 🔒 XSS protection

## Monitoring & Observability

```
┌─────────────────────────────────────────────────────────────────┐
│                    Spring Boot Actuator                          │
└─────────────────────────────────────────────────────────────────┘

/actuator/health     - Application health status
/actuator/info       - Application information
/actuator/metrics    - Performance metrics
/actuator/env        - Environment properties
/actuator/beans      - Spring beans
/actuator/mappings   - Request mappings
```

## API Documentation

```
┌─────────────────────────────────────────────────────────────────┐
│                      Swagger UI                                  │
│              http://localhost:8081/swagger-ui.html               │
└─────────────────────────────────────────────────────────────────┘

Features:
- Interactive API testing
- Request/response schemas
- Authentication testing
- Try out endpoints
- Download OpenAPI spec
```

---

**Last Updated**: May 17, 2026
**Version**: 1.0.0
