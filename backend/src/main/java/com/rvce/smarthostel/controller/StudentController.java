package com.rvce.smarthostel.controller;

import com.rvce.smarthostel.dto.AttendanceDTO;
import com.rvce.smarthostel.dto.AttendanceRequest;
import com.rvce.smarthostel.dto.MealChoiceDTO;
import com.rvce.smarthostel.dto.MealOptOutRequest;
import com.rvce.smarthostel.dto.StudentDTO;
import com.rvce.smarthostel.dto.MealMenuDTO;
import com.rvce.smarthostel.service.AttendanceService;
import com.rvce.smarthostel.service.MealService;
import com.rvce.smarthostel.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final AttendanceService attendanceService;
    private final MealService mealService;
    
    @GetMapping("/profile")
    public ResponseEntity<StudentDTO> getProfile(@RequestParam String studentId) {
        return studentService.getStudentById(studentId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/attendance")
    public ResponseEntity<AttendanceDTO> markAttendance(@Valid @RequestBody AttendanceRequest request) {
        AttendanceDTO attendance = attendanceService.markAttendance(request);
        return ResponseEntity.ok(attendance);
    }
    
    @GetMapping("/attendance")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceHistory(@RequestParam String studentId) {
        List<AttendanceDTO> history = attendanceService.getStudentAttendance(studentId);
        return ResponseEntity.ok(history);
    }
    
    @GetMapping("/attendance/range")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceByRange(
            @RequestParam String studentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<AttendanceDTO> history = attendanceService.getStudentAttendanceByDateRange(studentId, startDate, endDate);
        return ResponseEntity.ok(history);
    }
    
    @GetMapping("/attendance/status")
    public ResponseEntity<AttendanceDTO> getCurrentStatus(@RequestParam String studentId) {
        return attendanceService.getLatestStudentAttendance(studentId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/meals/optout")
    public ResponseEntity<MealChoiceDTO> submitOptOut(@Valid @RequestBody MealOptOutRequest request) {
        MealChoiceDTO optOut = mealService.submitOptOut(request);
        return ResponseEntity.ok(optOut);
    }
    
    @GetMapping("/meals/optouts")
    public ResponseEntity<List<MealChoiceDTO>> getMyOptOuts(@RequestParam String studentId) {
        List<MealChoiceDTO> optOuts = mealService.getStudentOptOuts(studentId);
        return ResponseEntity.ok(optOuts);
    }
    
    @GetMapping("/meals/menu/today")
    public ResponseEntity<List<MealMenuDTO>> getTodaysMenu() {
        List<MealMenuDTO> menu = mealService.getTodaysMenu();
        return ResponseEntity.ok(menu);
    }
}
