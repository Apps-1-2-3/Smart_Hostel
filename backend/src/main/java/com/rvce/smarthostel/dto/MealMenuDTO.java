package com.rvce.smarthostel.dto;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealMenuDTO {
    private Long poolId;
    private String day;
    private String mealTime;
    private String menuItem;
    private String category;
}
