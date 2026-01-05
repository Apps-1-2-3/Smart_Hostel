package com.rvce.smarthostel.dto;

import com.rvce.smarthostel.entity.MealChoice;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealOptOutRequest {
    @NotNull(message = "Date is required")
    private LocalDate date;
    
    @NotNull(message = "Meal time is required")
    private MealChoice.MealTime mealTime;
    
    private String studentId;
    
    private Boolean optOut = true;
}
