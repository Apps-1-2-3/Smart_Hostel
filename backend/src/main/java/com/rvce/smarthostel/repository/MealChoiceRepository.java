package com.rvce.smarthostel.repository;

import com.rvce.smarthostel.entity.MealChoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MealChoiceRepository extends JpaRepository<MealChoice, Long> {
    List<MealChoice> findByStudentStudentId(String studentId);
    
    List<MealChoice> findByDate(LocalDate date);
    
    List<MealChoice> findByDateAndMealTime(LocalDate date, MealChoice.MealTime mealTime);
    
    Optional<MealChoice> findByStudentStudentIdAndDateAndMealTime(
        String studentId, LocalDate date, MealChoice.MealTime mealTime
    );
    
    @Query("SELECT COUNT(mc) FROM MealChoice mc WHERE mc.date = :date AND mc.mealTime = :mealTime AND mc.optedOut = true")
    Long countOptOutsByDateAndMealTime(@Param("date") LocalDate date, @Param("mealTime") MealChoice.MealTime mealTime);
    
    @Query("SELECT mc.mealTime, COUNT(mc) FROM MealChoice mc WHERE mc.date BETWEEN :start AND :end AND mc.optedOut = true GROUP BY mc.mealTime")
    List<Object[]> getOptOutStatsByDateRange(@Param("start") LocalDate start, @Param("end") LocalDate end);
    
    @Query("SELECT mc.date, COUNT(mc) FROM MealChoice mc WHERE mc.date BETWEEN :start AND :end AND mc.optedOut = true GROUP BY mc.date ORDER BY mc.date")
    List<Object[]> getDailyOptOutTrend(@Param("start") LocalDate start, @Param("end") LocalDate end);
}
