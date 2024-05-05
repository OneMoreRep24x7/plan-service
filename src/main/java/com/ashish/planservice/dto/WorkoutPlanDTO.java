package com.ashish.planservice.dto;

import com.ashish.planservice.model.DailyWorkout;
import com.ashish.planservice.model.WorkoutPlan;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class WorkoutPlanDTO {
    private DailyWorkout todayWorkout;
    private WorkoutPlan workoutPlan;
    private String message;
    private int statusCode;
}
