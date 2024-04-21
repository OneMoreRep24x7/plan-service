package com.ashish.planservice.service;

import com.ashish.planservice.dto.*;
import com.ashish.planservice.model.DailyWorkout;
import com.ashish.planservice.model.WorkoutPlan;

import java.util.List;
import java.util.UUID;

public interface PlanService {
    CommonResponseDTO addDailyWorkout(DailyWorkoutParams dailyWorkoutParams);

    List<DailyWorkout> getDailyWorkouts(UUID ownerId);

    CommonResponseDTO addWorkoutPlan(WorkoutPlanParams workoutPlanParams);

    WorkoutPlanDTO getWorkoutPlan(WorkoutPlanGetParams planGetParams);

    List<WorkoutPlan> getTrainerWorkoutPlans(java.util.UUID trainerId);
}
