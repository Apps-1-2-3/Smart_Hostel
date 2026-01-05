package com.rvce.smarthostel.service;

import com.rvce.smarthostel.dto.MealChoiceDTO;
import com.rvce.smarthostel.dto.MealDemandDTO;
import com.rvce.smarthostel.dto.MealMenuDTO;
import com.rvce.smarthostel.dto.MealOptOutRequest;
import com.rvce.smarthostel.entity.MealChoice;
import com.rvce.smarthostel.entity.MealMenu;
import com.rvce.smarthostel.entity.Student;
import com.rvce.smarthostel.repository.MealChoiceRepository;
import com.rvce.smarthostel.repository.MealMenuRepository;
import com.rvce.smarthostel.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MealService {
    private final MealChoiceRepository mealChoiceRepository;
    private final MealMenuRepository mealMenuRepository;
    private final StudentRepository studentRepository;
    
    public MealChoiceDTO submitOptOut(MealOptOutRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
            .orElseThrow(() -> new RuntimeException("Student not found"));
        
        // Check if already exists
        Optional<MealChoice> existing = mealChoiceRepository.findByStudentStudentIdAndDateAndMealTime(
            request.getStudentId(), request.getDate(), request.getMealTime()
        );
        
        MealChoice mealChoice;
        if (existing.isPresent()) {
            mealChoice = existing.get();
            mealChoice.setOptedOut(request.getOptOut());
        } else {
            mealChoice = MealChoice.builder()
                .date(request.getDate())
                .mealTime(request.getMealTime())
                .student(student)
                .optedOut(request.getOptOut())
                .build();
        }
        
        MealChoice saved = mealChoiceRepository.save(mealChoice);
        return toChoiceDTO(saved);
    }
    
    public List<MealChoiceDTO> getStudentOptOuts(String studentId) {
        return mealChoiceRepository.findByStudentStudentId(studentId)
            .stream()
            .map(this::toChoiceDTO)
            .collect(Collectors.toList());
    }
    
    public List<MealChoiceDTO> getOptOutsByDate(LocalDate date) {
        return mealChoiceRepository.findByDate(date)
            .stream()
            .map(this::toChoiceDTO)
            .collect(Collectors.toList());
    }
    
    public MealDemandDTO getMealDemand(LocalDate date) {
        Long totalStudents = studentRepository.count();
        
        Map<String, MealDemandDTO.MealStats> mealStats = new HashMap<>();
        
        for (MealChoice.MealTime mealTime : MealChoice.MealTime.values()) {
            Long optedOut = mealChoiceRepository.countOptOutsByDateAndMealTime(date, mealTime);
            Long eating = totalStudents - optedOut;
            
            mealStats.put(mealTime.name(), MealDemandDTO.MealStats.builder()
                .expected(totalStudents)
                .optedOut(optedOut)
                .eating(eating)
                .build());
        }
        
        return MealDemandDTO.builder()
            .date(date.toString())
            .totalStudents(totalStudents)
            .mealStats(mealStats)
            .build();
    }
    
    public Map<String, Long> getOptOutStats(LocalDate startDate, LocalDate endDate) {
        List<Object[]> results = mealChoiceRepository.getOptOutStatsByDateRange(startDate, endDate);
        Map<String, Long> stats = new HashMap<>();
        
        for (Object[] row : results) {
            MealChoice.MealTime mealTime = (MealChoice.MealTime) row[0];
            Long count = (Long) row[1];
            stats.put(mealTime.name(), count);
        }
        
        return stats;
    }
    
    public List<MealMenuDTO> getTodaysMenu() {
        DayOfWeek today = LocalDate.now().getDayOfWeek();
        MealMenu.DayOfWeek day = MealMenu.DayOfWeek.valueOf(today.name());
        
        return mealMenuRepository.findByDay(day)
            .stream()
            .map(this::toMenuDTO)
            .collect(Collectors.toList());
    }
    
    public List<MealMenuDTO> getMenuByDay(MealMenu.DayOfWeek day) {
        return mealMenuRepository.findByDay(day)
            .stream()
            .map(this::toMenuDTO)
            .collect(Collectors.toList());
    }
    
    public List<MealMenuDTO> getAllMenus() {
        return mealMenuRepository.findAll()
            .stream()
            .map(this::toMenuDTO)
            .collect(Collectors.toList());
    }
    
    private MealChoiceDTO toChoiceDTO(MealChoice choice) {
        return MealChoiceDTO.builder()
            .optId(choice.getOptId())
            .date(choice.getDate())
            .mealTime(choice.getMealTime().name())
            .studentId(choice.getStudent().getStudentId())
            .studentName(choice.getStudent().getFullName())
            .optedOut(choice.getOptedOut())
            .build();
    }
    
    private MealMenuDTO toMenuDTO(MealMenu menu) {
        return MealMenuDTO.builder()
            .poolId(menu.getPoolId())
            .day(menu.getDay().name())
            .mealTime(menu.getMealTime().name())
            .menuItem(menu.getMenuItem())
            .category(menu.getCategory())
            .build();
    }
}
