package com.rvce.smarthostel.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "MEALMENU")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PoolID")
    private Long poolId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "Day", nullable = false)
    private DayOfWeek day;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "MealTime", nullable = false)
    private MealChoice.MealTime mealTime;
    
    @Column(name = "MenuItem", nullable = false)
    private String menuItem;
    
    @Column(name = "Category")
    private String category;
    
    public enum DayOfWeek {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }
}
