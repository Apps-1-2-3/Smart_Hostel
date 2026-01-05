import API_CONFIG from '@/config/api.config';

// Types for API responses
export interface LoginResponse {
  token: string;
  email: string;
  name: string;
  role: string;
  roomNumber?: string;
  studentId?: string;
}

export interface AttendanceDTO {
  id: number;
  studentId: string;
  studentName: string;
  timestamp: string;
  type: 'IN' | 'OUT';
  location?: string;
  verifiedBy?: string;
}

export interface StudentDTO {
  studentId: string;
  firstName: string;
  middleName?: string;
  lastName: string;
  fullName: string;
  roomNo: string;
  hostelNo: string;
  status: string;
  phoneNumber?: string;
}

export interface RoomDTO {
  roomNo: string;
  capacity: number;
  hostelNo: string;
  floor: number;
  currentOccupancy: number;
  status: string;
}

export interface MealChoiceDTO {
  optId: number;
  date: string;
  mealTime: string;
  studentId: string;
  studentName: string;
  optedOut: boolean;
}

export interface MealMenuDTO {
  poolId: number;
  day: string;
  mealTime: string;
  menuItem: string;
  category?: string;
}

export interface MealDemandDTO {
  date: string;
  totalStudents: number;
  mealStats: {
    [key: string]: {
      expected: number;
      optedOut: number;
      eating: number;
    };
  };
}

export interface AnalyticsDTO {
  totalStudents: number;
  presentToday: number;
  absentToday: number;
  attendancePercentage: number;
  mealOptOuts: { [key: string]: number };
  roomOccupancy: { [key: string]: number };
  hourlyCheckIns: { [key: number]: number };
  hourlyCheckOuts: { [key: number]: number };
}

// API Error type
export class ApiError extends Error {
  constructor(public status: number, message: string) {
    super(message);
    this.name = 'ApiError';
  }
}

// Get auth token from localStorage
const getAuthToken = (): string | null => {
  return localStorage.getItem('smartHostel_token');
};

// Base fetch wrapper with auth headers
const apiFetch = async <T>(
  endpoint: string,
  options: RequestInit = {}
): Promise<T> => {
  const token = getAuthToken();
  
  const headers: HeadersInit = {
    'Content-Type': 'application/json',
    ...options.headers,
  };
  
  if (token && token !== 'mock_jwt_token') {
    headers['Authorization'] = `Bearer ${token}`;
  }
  
  const response = await fetch(`${API_CONFIG.BASE_URL}${endpoint}`, {
    ...options,
    headers,
  });
  
  if (!response.ok) {
    const errorData = await response.json().catch(() => ({ message: 'Request failed' }));
    throw new ApiError(response.status, errorData.message || 'Request failed');
  }
  
  return response.json();
};

// Auth API
export const authApi = {
  login: async (email: string, password: string): Promise<LoginResponse> => {
    return apiFetch<LoginResponse>(API_CONFIG.ENDPOINTS.LOGIN, {
      method: 'POST',
      body: JSON.stringify({ email, password }),
    });
  },
  
  logout: async (): Promise<void> => {
    await apiFetch(API_CONFIG.ENDPOINTS.LOGOUT, { method: 'POST' });
  },
  
  validateToken: async (): Promise<boolean> => {
    try {
      await apiFetch(API_CONFIG.ENDPOINTS.VALIDATE);
      return true;
    } catch {
      return false;
    }
  },
};

// Student API
export const studentApi = {
  getProfile: async (studentId: string): Promise<StudentDTO> => {
    return apiFetch<StudentDTO>(`${API_CONFIG.ENDPOINTS.STUDENT_PROFILE}?studentId=${studentId}`);
  },
  
  markAttendance: async (studentId: string, type: 'IN' | 'OUT', location?: string): Promise<AttendanceDTO> => {
    return apiFetch<AttendanceDTO>(API_CONFIG.ENDPOINTS.STUDENT_ATTENDANCE, {
      method: 'POST',
      body: JSON.stringify({ studentId, type, location }),
    });
  },
  
  getAttendanceHistory: async (studentId: string): Promise<AttendanceDTO[]> => {
    return apiFetch<AttendanceDTO[]>(`${API_CONFIG.ENDPOINTS.STUDENT_ATTENDANCE}?studentId=${studentId}`);
  },
  
  getAttendanceByRange: async (studentId: string, startDate: string, endDate: string): Promise<AttendanceDTO[]> => {
    return apiFetch<AttendanceDTO[]>(
      `${API_CONFIG.ENDPOINTS.STUDENT_ATTENDANCE_RANGE}?studentId=${studentId}&startDate=${startDate}&endDate=${endDate}`
    );
  },
  
  getCurrentStatus: async (studentId: string): Promise<AttendanceDTO> => {
    return apiFetch<AttendanceDTO>(`${API_CONFIG.ENDPOINTS.STUDENT_ATTENDANCE_STATUS}?studentId=${studentId}`);
  },
  
  submitMealOptOut: async (studentId: string, date: string, mealTime: string, optOut: boolean = true): Promise<MealChoiceDTO> => {
    return apiFetch<MealChoiceDTO>(API_CONFIG.ENDPOINTS.STUDENT_MEAL_OPTOUT, {
      method: 'POST',
      body: JSON.stringify({ studentId, date, mealTime, optOut }),
    });
  },
  
  getMealOptOuts: async (studentId: string): Promise<MealChoiceDTO[]> => {
    return apiFetch<MealChoiceDTO[]>(`${API_CONFIG.ENDPOINTS.STUDENT_MEAL_OPTOUTS}?studentId=${studentId}`);
  },
  
  getTodaysMenu: async (): Promise<MealMenuDTO[]> => {
    return apiFetch<MealMenuDTO[]>(API_CONFIG.ENDPOINTS.STUDENT_MENU_TODAY);
  },
};

// Admin API
export const adminApi = {
  getAnalytics: async (): Promise<AnalyticsDTO> => {
    return apiFetch<AnalyticsDTO>(API_CONFIG.ENDPOINTS.ADMIN_ANALYTICS);
  },
  
  getStudents: async (): Promise<StudentDTO[]> => {
    return apiFetch<StudentDTO[]>(API_CONFIG.ENDPOINTS.ADMIN_STUDENTS);
  },
  
  getStudent: async (studentId: string): Promise<StudentDTO> => {
    return apiFetch<StudentDTO>(`${API_CONFIG.ENDPOINTS.ADMIN_STUDENTS}/${studentId}`);
  },
  
  getStudentsByHostel: async (hostelNo: string): Promise<StudentDTO[]> => {
    return apiFetch<StudentDTO[]>(`${API_CONFIG.ENDPOINTS.ADMIN_STUDENTS}/hostel/${hostelNo}`);
  },
  
  createStudent: async (student: Partial<StudentDTO>): Promise<StudentDTO> => {
    return apiFetch<StudentDTO>(API_CONFIG.ENDPOINTS.ADMIN_STUDENTS, {
      method: 'POST',
      body: JSON.stringify(student),
    });
  },
  
  updateStudent: async (studentId: string, student: Partial<StudentDTO>): Promise<StudentDTO> => {
    return apiFetch<StudentDTO>(`${API_CONFIG.ENDPOINTS.ADMIN_STUDENTS}/${studentId}`, {
      method: 'PUT',
      body: JSON.stringify(student),
    });
  },
  
  deleteStudent: async (studentId: string): Promise<void> => {
    await apiFetch(`${API_CONFIG.ENDPOINTS.ADMIN_STUDENTS}/${studentId}`, { method: 'DELETE' });
  },
  
  getAttendance: async (startDate: string, endDate: string): Promise<AttendanceDTO[]> => {
    return apiFetch<AttendanceDTO[]>(
      `${API_CONFIG.ENDPOINTS.ADMIN_ATTENDANCE}?startDate=${startDate}&endDate=${endDate}`
    );
  },
  
  getTodaysAttendance: async (): Promise<AttendanceDTO[]> => {
    return apiFetch<AttendanceDTO[]>(API_CONFIG.ENDPOINTS.ADMIN_ATTENDANCE_TODAY);
  },
  
  getHourlyStats: async (date: string): Promise<{ checkIns: { [key: number]: number }; checkOuts: { [key: number]: number } }> => {
    return apiFetch(`${API_CONFIG.ENDPOINTS.ADMIN_ATTENDANCE_HOURLY}?date=${date}`);
  },
  
  getRooms: async (): Promise<RoomDTO[]> => {
    return apiFetch<RoomDTO[]>(API_CONFIG.ENDPOINTS.ADMIN_ROOMS);
  },
  
  getRoomsByHostel: async (hostelNo: string): Promise<RoomDTO[]> => {
    return apiFetch<RoomDTO[]>(`${API_CONFIG.ENDPOINTS.ADMIN_ROOMS}/hostel/${hostelNo}`);
  },
  
  getRoomOccupancy: async (): Promise<{ [key: string]: number }> => {
    return apiFetch(API_CONFIG.ENDPOINTS.ADMIN_ROOMS_OCCUPANCY);
  },
  
  getMealOptOutStats: async (startDate: string, endDate: string): Promise<{ [key: string]: number }> => {
    return apiFetch(`${API_CONFIG.ENDPOINTS.ADMIN_MEALS_OPTOUTS}?startDate=${startDate}&endDate=${endDate}`);
  },
  
  getMealDemand: async (date: string): Promise<MealDemandDTO> => {
    return apiFetch<MealDemandDTO>(`${API_CONFIG.ENDPOINTS.ADMIN_MEALS_DEMAND}?date=${date}`);
  },
};

// Mess API
export const messApi = {
  getDemand: async (date: string): Promise<MealDemandDTO> => {
    return apiFetch<MealDemandDTO>(`${API_CONFIG.ENDPOINTS.MESS_DEMAND}?date=${date}`);
  },
  
  getTodaysDemand: async (): Promise<MealDemandDTO> => {
    return apiFetch<MealDemandDTO>(API_CONFIG.ENDPOINTS.MESS_DEMAND_TODAY);
  },
  
  getOptOutsByDate: async (date: string): Promise<MealChoiceDTO[]> => {
    return apiFetch<MealChoiceDTO[]>(`${API_CONFIG.ENDPOINTS.MESS_OPTOUTS}?date=${date}`);
  },
  
  getOptOutStats: async (startDate: string, endDate: string): Promise<{ [key: string]: number }> => {
    return apiFetch(`${API_CONFIG.ENDPOINTS.MESS_STATS}?startDate=${startDate}&endDate=${endDate}`);
  },
  
  getAllMenus: async (): Promise<MealMenuDTO[]> => {
    return apiFetch<MealMenuDTO[]>(API_CONFIG.ENDPOINTS.MESS_MENU);
  },
  
  getTodaysMenu: async (): Promise<MealMenuDTO[]> => {
    return apiFetch<MealMenuDTO[]>(API_CONFIG.ENDPOINTS.MESS_MENU_TODAY);
  },
  
  getTotalStudentCount: async (): Promise<number> => {
    return apiFetch<number>(API_CONFIG.ENDPOINTS.MESS_STUDENTS_COUNT);
  },
};
