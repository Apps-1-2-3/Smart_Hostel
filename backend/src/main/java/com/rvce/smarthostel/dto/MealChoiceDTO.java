package com.rvce.smarthostel.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealChoiceDTO {
    private Long optId;
    private LocalDate date;
    private String mealTime;
    private String studentId;
    private String studentName;
    private Boolean optedOut;
}
