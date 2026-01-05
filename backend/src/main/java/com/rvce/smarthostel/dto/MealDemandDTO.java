package com.rvce.smarthostel.dto;

import lombok.*;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealDemandDTO {
    private String date;
    private Long totalStudents;
    private Map<String, MealStats> mealStats;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MealStats {
        private Long expected;
        private Long optedOut;
        private Long eating;
    }
}
