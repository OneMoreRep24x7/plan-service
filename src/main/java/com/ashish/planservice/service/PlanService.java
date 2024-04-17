package com.ashish.planservice.service;

import com.ashish.planservice.dto.CommonResponseDTO;
import com.ashish.planservice.dto.DailyWorkoutParams;
import com.ashish.planservice.dto.WorkoutPlanGetParams;
import com.ashish.planservice.dto.WorkoutPlanParams;
import com.ashish.planservice.model.DailyWorkout;
import com.ashish.planservice.model.WorkoutPlan;

import java.util.List;
import java.util.UUID;

public interface PlanService {
    CommonResponseDTO addDailyWorkout(DailyWorkoutParams dailyWorkoutParams);

    List<DailyWorkout> getDailyWorkouts(UUID ownerId);

    CommonResponseDTO addWorkoutPlan(WorkoutPlanParams workoutPlanParams);

    WorkoutPlan getWorkoutPlan(WorkoutPlanGetParams planGetParams);
}
