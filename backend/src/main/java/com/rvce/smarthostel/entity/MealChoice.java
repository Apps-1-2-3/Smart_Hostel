package com.rvce.smarthostel.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "CHOICE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealChoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OptID")
    private Long optId;
    
    @Column(name = "Date", nullable = false)
    private LocalDate date;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "MealTime", nullable = false)
    private MealTime mealTime;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StudentID", nullable = false)
    private Student student;
    
    @Column(name = "OptedOut")
    private Boolean optedOut = true;
    
    public enum MealTime {
        BREAKFAST, LUNCH, DINNER
    }
}
