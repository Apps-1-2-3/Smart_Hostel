package com.rvce.smarthostel.dto;

import lombok.*;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalyticsDTO {
    private Long totalStudents;
    private Long presentToday;
    private Long absentToday;
    private Double attendancePercentage;
    private Map<String, Long> mealOptOuts;
    private Map<String, Long> roomOccupancy;
    private Map<Integer, Long> hourlyCheckIns;
    private Map<Integer, Long> hourlyCheckOuts;
}
