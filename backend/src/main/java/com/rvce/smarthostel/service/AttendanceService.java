package com.rvce.smarthostel.service;

import com.rvce.smarthostel.dto.AttendanceDTO;
import com.rvce.smarthostel.dto.AttendanceRequest;
import com.rvce.smarthostel.entity.Attendance;
import com.rvce.smarthostel.entity.Employee;
import com.rvce.smarthostel.entity.Student;
import com.rvce.smarthostel.repository.AttendanceRepository;
import com.rvce.smarthostel.repository.EmployeeRepository;
import com.rvce.smarthostel.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final EmployeeRepository employeeRepository;
    
    public AttendanceDTO markAttendance(AttendanceRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
            .orElseThrow(() -> new RuntimeException("Student not found"));
        
        Employee verifiedBy = null;
        if (request.getEmployeeSSN() != null) {
            verifiedBy = employeeRepository.findById(request.getEmployeeSSN()).orElse(null);
        }
        
        Attendance attendance = Attendance.builder()
            .student(student)
            .timestamp(LocalDateTime.now())
            .type(request.getType())
            .location(request.getLocation())
            .verifiedBy(verifiedBy)
            .build();
        
        Attendance saved = attendanceRepository.save(attendance);
        return toDTO(saved);
    }
    
    public List<AttendanceDTO> getStudentAttendance(String studentId) {
        return attendanceRepository.findByStudentStudentIdOrderByTimestampDesc(studentId)
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    public List<AttendanceDTO> getStudentAttendanceByDateRange(String studentId, LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);
        
        return attendanceRepository.findByStudentAndDateRange(studentId, start, end)
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    public List<AttendanceDTO> getAllAttendanceByDateRange(LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);
        
        return attendanceRepository.findByDateRange(start, end)
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    public List<AttendanceDTO> getTodaysAttendance() {
        LocalDate today = LocalDate.now();
        return getAllAttendanceByDateRange(today, today);
    }
    
    public Optional<AttendanceDTO> getLatestStudentAttendance(String studentId) {
        return attendanceRepository.findLatestByStudent(studentId).map(this::toDTO);
    }
    
    public Map<Integer, Long> getHourlyCheckIns(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);
        
        List<Object[]> results = attendanceRepository.getHourlyDistribution(Attendance.AttendanceType.IN, start, end);
        Map<Integer, Long> hourlyData = new HashMap<>();
        for (int i = 0; i < 24; i++) hourlyData.put(i, 0L);
        
        for (Object[] row : results) {
            Integer hour = (Integer) row[0];
            Long count = (Long) row[1];
            hourlyData.put(hour, count);
        }
        return hourlyData;
    }
    
    public Map<Integer, Long> getHourlyCheckOuts(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);
        
        List<Object[]> results = attendanceRepository.getHourlyDistribution(Attendance.AttendanceType.OUT, start, end);
        Map<Integer, Long> hourlyData = new HashMap<>();
        for (int i = 0; i < 24; i++) hourlyData.put(i, 0L);
        
        for (Object[] row : results) {
            Integer hour = (Integer) row[0];
            Long count = (Long) row[1];
            hourlyData.put(hour, count);
        }
        return hourlyData;
    }
    
    public Long countPresentToday() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDateTime.now();
        return attendanceRepository.countCheckIns(start, end);
    }
    
    private AttendanceDTO toDTO(Attendance attendance) {
        return AttendanceDTO.builder()
            .id(attendance.getId())
            .studentId(attendance.getStudent().getStudentId())
            .studentName(attendance.getStudent().getFullName())
            .timestamp(attendance.getTimestamp())
            .type(attendance.getType().name())
            .location(attendance.getLocation())
            .verifiedBy(attendance.getVerifiedBy() != null ? attendance.getVerifiedBy().getFullName() : null)
            .build();
    }
}
