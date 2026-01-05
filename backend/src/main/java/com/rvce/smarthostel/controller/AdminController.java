package com.rvce.smarthostel.controller;

import com.rvce.smarthostel.dto.*;
import com.rvce.smarthostel.entity.Student;
import com.rvce.smarthostel.entity.Room;
import com.rvce.smarthostel.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final StudentService studentService;
    private final AttendanceService attendanceService;
    private final RoomService roomService;
    private final MealService mealService;
    private final AnalyticsService analyticsService;
    
    // Dashboard Analytics
    @GetMapping("/analytics")
    public ResponseEntity<AnalyticsDTO> getDashboardAnalytics() {
        AnalyticsDTO analytics = analyticsService.getDashboardAnalytics();
        return ResponseEntity.ok(analytics);
    }
    
    // Student Management
    @GetMapping("/students")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }
    
    @GetMapping("/students/{studentId}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable String studentId) {
        return studentService.getStudentById(studentId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/students/hostel/{hostelNo}")
    public ResponseEntity<List<StudentDTO>> getStudentsByHostel(@PathVariable String hostelNo) {
        List<StudentDTO> students = studentService.getStudentsByHostel(hostelNo);
        return ResponseEntity.ok(students);
    }
    
    @PostMapping("/students")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student created = studentService.createStudent(student);
        return ResponseEntity.ok(created);
    }
    
    @PutMapping("/students/{studentId}")
    public ResponseEntity<Student> updateStudent(@PathVariable String studentId, @RequestBody Student student) {
        Student updated = studentService.updateStudent(studentId, student);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/students/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.ok().build();
    }
    
    // Attendance Management
    @GetMapping("/attendance")
    public ResponseEntity<List<AttendanceDTO>> getAllAttendance(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<AttendanceDTO> attendance = attendanceService.getAllAttendanceByDateRange(startDate, endDate);
        return ResponseEntity.ok(attendance);
    }
    
    @GetMapping("/attendance/today")
    public ResponseEntity<List<AttendanceDTO>> getTodaysAttendance() {
        List<AttendanceDTO> attendance = attendanceService.getTodaysAttendance();
        return ResponseEntity.ok(attendance);
    }
    
    @GetMapping("/attendance/hourly")
    public ResponseEntity<Map<String, Map<Integer, Long>>> getHourlyStats(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Map<Integer, Long> checkIns = attendanceService.getHourlyCheckIns(date);
        Map<Integer, Long> checkOuts = attendanceService.getHourlyCheckOuts(date);
        return ResponseEntity.ok(Map.of("checkIns", checkIns, "checkOuts", checkOuts));
    }
    
    // Room Management
    @GetMapping("/rooms")
    public ResponseEntity<List<RoomDTO>> getAllRooms() {
        List<RoomDTO> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }
    
    @GetMapping("/rooms/hostel/{hostelNo}")
    public ResponseEntity<List<RoomDTO>> getRoomsByHostel(@PathVariable String hostelNo) {
        List<RoomDTO> rooms = roomService.getRoomsByHostel(hostelNo);
        return ResponseEntity.ok(rooms);
    }
    
    @GetMapping("/rooms/occupancy")
    public ResponseEntity<Map<String, Long>> getOccupancyByHostel() {
        Map<String, Long> occupancy = roomService.getOccupancyByHostel();
        return ResponseEntity.ok(occupancy);
    }
    
    @PostMapping("/rooms")
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        Room created = roomService.createRoom(room);
        return ResponseEntity.ok(created);
    }
    
    @PutMapping("/rooms/{roomNo}")
    public ResponseEntity<Room> updateRoom(@PathVariable String roomNo, @RequestBody Room room) {
        Room updated = roomService.updateRoom(roomNo, room);
        return ResponseEntity.ok(updated);
    }
    
    // Meal Stats
    @GetMapping("/meals/optouts")
    public ResponseEntity<Map<String, Long>> getMealOptOutStats(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Map<String, Long> stats = mealService.getOptOutStats(startDate, endDate);
        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/meals/demand")
    public ResponseEntity<MealDemandDTO> getMealDemand(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        MealDemandDTO demand = mealService.getMealDemand(date);
        return ResponseEntity.ok(demand);
    }
}
