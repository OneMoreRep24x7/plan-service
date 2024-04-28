package com.ashish.planservice.dto;

import com.ashish.planservice.model.DailyDiet;
import com.ashish.planservice.model.DailyWorkout;
import com.ashish.planservice.model.DietPlan;
import com.ashish.planservice.model.WorkoutPlan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DietPlanDTO {
    private DailyDietDTO  todayDiet;
    private DietPlan dietPlan;
    private String message;
    private int statusCode;
}
