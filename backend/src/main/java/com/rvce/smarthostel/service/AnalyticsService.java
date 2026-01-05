package com.rvce.smarthostel.service;

import com.rvce.smarthostel.dto.AnalyticsDTO;
import com.rvce.smarthostel.entity.MealChoice;
import com.rvce.smarthostel.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    private final StudentRepository studentRepository;
    private final AttendanceRepository attendanceRepository;
    private final MealChoiceRepository mealChoiceRepository;
    private final RoomAssignmentRepository roomAssignmentRepository;
    
    public AnalyticsDTO getDashboardAnalytics() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);
        
        Long totalStudents = studentRepository.count();
        Long presentToday = attendanceRepository.countCheckIns(startOfDay, endOfDay);
        Long absentToday = totalStudents - presentToday;
        Double attendancePercentage = totalStudents > 0 ? (presentToday * 100.0 / totalStudents) : 0.0;
        
        // Meal opt-outs for today
        Map<String, Long> mealOptOuts = new HashMap<>();
        for (MealChoice.MealTime mealTime : MealChoice.MealTime.values()) {
            Long count = mealChoiceRepository.countOptOutsByDateAndMealTime(today, mealTime);
            mealOptOuts.put(mealTime.name(), count);
        }
        
        // Room occupancy by hostel
        Map<String, Long> roomOccupancy = new HashMap<>();
        List<Object[]> occupancyResults = roomAssignmentRepository.getOccupancyByHostel();
        for (Object[] row : occupancyResults) {
            roomOccupancy.put((String) row[0], (Long) row[1]);
        }
        
        // Hourly check-ins
        Map<Integer, Long> hourlyCheckIns = new HashMap<>();
        Map<Integer, Long> hourlyCheckOuts = new HashMap<>();
        for (int i = 0; i < 24; i++) {
            hourlyCheckIns.put(i, 0L);
            hourlyCheckOuts.put(i, 0L);
        }
        
        List<Object[]> checkInResults = attendanceRepository.getHourlyDistribution(
            com.rvce.smarthostel.entity.Attendance.AttendanceType.IN, startOfDay, endOfDay
        );
        for (Object[] row : checkInResults) {
            hourlyCheckIns.put((Integer) row[0], (Long) row[1]);
        }
        
        List<Object[]> checkOutResults = attendanceRepository.getHourlyDistribution(
            com.rvce.smarthostel.entity.Attendance.AttendanceType.OUT, startOfDay, endOfDay
        );
        for (Object[] row : checkOutResults) {
            hourlyCheckOuts.put((Integer) row[0], (Long) row[1]);
        }
        
        return AnalyticsDTO.builder()
            .totalStudents(totalStudents)
            .presentToday(presentToday)
            .absentToday(absentToday)
            .attendancePercentage(attendancePercentage)
            .mealOptOuts(mealOptOuts)
            .roomOccupancy(roomOccupancy)
            .hourlyCheckIns(hourlyCheckIns)
            .hourlyCheckOuts(hourlyCheckOuts)
            .build();
    }
}
