# SmartHostel Backend

A Spring Boot backend for the SmartHostel Intelligent Attendance and Mess Management System.

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0.19

## Database Setup

1. Start MySQL server
2. Run the schema creation script:
   ```bash
   mysql -u root -p < database/schema.sql
   ```
3. Load sample data:
   ```bash
   mysql -u root -p < database/sample-data.sql
   ```

## Configuration

1. Edit `src/main/resources/application.properties`:
   - Update `spring.datasource.password` with your MySQL password
   - Adjust other settings as needed

## Running the Application

### Using Maven

```bash
cd backend
mvn spring-boot:run
```

### Building a JAR

```bash
cd backend
mvn clean package
java -jar target/smarthostel-1.0.0.jar
```

## API Endpoints

### Authentication
- `POST /api/auth/login` - Login with email and password
- `POST /api/auth/logout` - Logout
- `GET /api/auth/validate` - Validate JWT token

### Student APIs (Requires STUDENT or ADMIN role)
- `GET /api/student/profile` - Get student profile
- `POST /api/student/attendance` - Mark attendance (IN/OUT)
- `GET /api/student/attendance` - Get attendance history
- `GET /api/student/attendance/range` - Get attendance by date range
- `GET /api/student/attendance/status` - Get current status
- `POST /api/student/meals/optout` - Submit meal opt-out
- `GET /api/student/meals/optouts` - Get meal opt-outs
- `GET /api/student/meals/menu/today` - Get today's menu

### Admin APIs (Requires ADMIN role)
- `GET /api/admin/analytics` - Dashboard analytics
- `GET /api/admin/students` - List all students
- `GET /api/admin/students/{id}` - Get student by ID
- `POST /api/admin/students` - Create student
- `PUT /api/admin/students/{id}` - Update student
- `DELETE /api/admin/students/{id}` - Delete student
- `GET /api/admin/attendance` - Get attendance logs
- `GET /api/admin/attendance/today` - Today's attendance
- `GET /api/admin/attendance/hourly` - Hourly distribution
- `GET /api/admin/rooms` - List all rooms
- `GET /api/admin/rooms/occupancy` - Room occupancy by hostel
- `GET /api/admin/meals/demand` - Meal demand for a date

### Mess Staff APIs (Requires MESS_STAFF or ADMIN role)
- `GET /api/mess/demand` - Get meal demand
- `GET /api/mess/demand/today` - Today's demand
- `GET /api/mess/optouts` - Opt-outs by date
- `GET /api/mess/stats` - Opt-out statistics
- `GET /api/mess/menu` - All menus
- `GET /api/mess/menu/today` - Today's menu

## Demo Credentials

| Role       | Email               | Password     |
|------------|---------------------|--------------|
| Student    | student@rvce.edu.in | password123  |
| Admin      | admin@rvce.edu.in   | password123  |
| Mess Staff | mess@rvce.edu.in    | password123  |

## CORS Configuration

The backend allows requests from:
- http://localhost:5173 (Vite dev server)
- http://localhost:3000
- http://localhost:8080

Update `SecurityConfig.java` to add more origins if needed.

## Security

- JWT-based authentication
- BCrypt password hashing
- Role-based access control (STUDENT, ADMIN, MESS_STAFF)
- CORS protection

## Project Structure

```
backend/
├── src/main/java/com/rvce/smarthostel/
│   ├── SmartHostelApplication.java
│   ├── config/
│   │   └── SecurityConfig.java
│   ├── controller/
│   │   ├── AuthController.java
│   │   ├── StudentController.java
│   │   ├── AdminController.java
│   │   └── MessController.java
│   ├── dto/
│   │   └── ... (Data Transfer Objects)
│   ├── entity/
│   │   └── ... (JPA Entities)
│   ├── exception/
│   │   └── GlobalExceptionHandler.java
│   ├── repository/
│   │   └── ... (JPA Repositories)
│   ├── security/
│   │   ├── JwtTokenProvider.java
│   │   ├── JwtAuthenticationFilter.java
│   │   └── CustomUserDetailsService.java
│   └── service/
│       └── ... (Business Logic)
├── src/main/resources/
│   └── application.properties
├── database/
│   ├── schema.sql
│   └── sample-data.sql
└── pom.xml
```
