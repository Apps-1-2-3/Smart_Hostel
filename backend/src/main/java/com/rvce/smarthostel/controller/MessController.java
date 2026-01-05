package com.rvce.smarthostel.controller;

import com.rvce.smarthostel.dto.*;
import com.rvce.smarthostel.service.MealService;
import com.rvce.smarthostel.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mess")
@RequiredArgsConstructor
public class MessController {
    private final MealService mealService;
    private final StudentService studentService;
    
    @GetMapping("/demand")
    public ResponseEntity<MealDemandDTO> getMealDemand(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        MealDemandDTO demand = mealService.getMealDemand(date);
        return ResponseEntity.ok(demand);
    }
    
    @GetMapping("/demand/today")
    public ResponseEntity<MealDemandDTO> getTodaysDemand() {
        MealDemandDTO demand = mealService.getMealDemand(LocalDate.now());
        return ResponseEntity.ok(demand);
    }
    
    @GetMapping("/optouts")
    public ResponseEntity<List<MealChoiceDTO>> getOptOutsByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<MealChoiceDTO> optOuts = mealService.getOptOutsByDate(date);
        return ResponseEntity.ok(optOuts);
    }
    
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getOptOutStats(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Map<String, Long> stats = mealService.getOptOutStats(startDate, endDate);
        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/menu")
    public ResponseEntity<List<MealMenuDTO>> getAllMenus() {
        List<MealMenuDTO> menus = mealService.getAllMenus();
        return ResponseEntity.ok(menus);
    }
    
    @GetMapping("/menu/today")
    public ResponseEntity<List<MealMenuDTO>> getTodaysMenu() {
        List<MealMenuDTO> menu = mealService.getTodaysMenu();
        return ResponseEntity.ok(menu);
    }
    
    @GetMapping("/students/count")
    public ResponseEntity<Long> getTotalStudentCount() {
        Long count = studentService.getTotalStudentCount();
        return ResponseEntity.ok(count);
    }
}
