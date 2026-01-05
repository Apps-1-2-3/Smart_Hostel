package com.rvce.smarthostel.repository;

import com.rvce.smarthostel.entity.MealMenu;
import com.rvce.smarthostel.entity.MealChoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MealMenuRepository extends JpaRepository<MealMenu, Long> {
    List<MealMenu> findByDay(MealMenu.DayOfWeek day);
    List<MealMenu> findByDayAndMealTime(MealMenu.DayOfWeek day, MealChoice.MealTime mealTime);
}
