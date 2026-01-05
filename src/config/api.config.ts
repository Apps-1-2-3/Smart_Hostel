// API Configuration for SmartHostel Backend
// When running locally, the Spring Boot backend runs on port 8080

const API_CONFIG = {
  // Change this to your deployed backend URL in production
  BASE_URL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080',
  
  // API endpoints
  ENDPOINTS: {
    // Auth
    LOGIN: '/api/auth/login',
    LOGOUT: '/api/auth/logout',
    VALIDATE: '/api/auth/validate',
    
    // Student
    STUDENT_PROFILE: '/api/student/profile',
    STUDENT_ATTENDANCE: '/api/student/attendance',
    STUDENT_ATTENDANCE_RANGE: '/api/student/attendance/range',
    STUDENT_ATTENDANCE_STATUS: '/api/student/attendance/status',
    STUDENT_MEAL_OPTOUT: '/api/student/meals/optout',
    STUDENT_MEAL_OPTOUTS: '/api/student/meals/optouts',
    STUDENT_MENU_TODAY: '/api/student/meals/menu/today',
    
    // Admin
    ADMIN_ANALYTICS: '/api/admin/analytics',
    ADMIN_STUDENTS: '/api/admin/students',
    ADMIN_ATTENDANCE: '/api/admin/attendance',
    ADMIN_ATTENDANCE_TODAY: '/api/admin/attendance/today',
    ADMIN_ATTENDANCE_HOURLY: '/api/admin/attendance/hourly',
    ADMIN_ROOMS: '/api/admin/rooms',
    ADMIN_ROOMS_OCCUPANCY: '/api/admin/rooms/occupancy',
    ADMIN_MEALS_OPTOUTS: '/api/admin/meals/optouts',
    ADMIN_MEALS_DEMAND: '/api/admin/meals/demand',
    
    // Mess Staff
    MESS_DEMAND: '/api/mess/demand',
    MESS_DEMAND_TODAY: '/api/mess/demand/today',
    MESS_OPTOUTS: '/api/mess/optouts',
    MESS_STATS: '/api/mess/stats',
    MESS_MENU: '/api/mess/menu',
    MESS_MENU_TODAY: '/api/mess/menu/today',
    MESS_STUDENTS_COUNT: '/api/mess/students/count',
  },
};

export default API_CONFIG;
