package com.ashish.planservice.service;

import com.ashish.planservice.dto.*;
import com.ashish.planservice.model.Tracking;

import java.util.List;
import java.util.UUID;

public interface TrackingService {
    WorkoutTrackingDTO updateWorkoutTracking(WorkoutTrackingParams workoutTrackingParams);

    WorkoutTrackingDTO getLatestWorkoutTracking(UUID userId);

    CommonResponseDTO removeWorkoutFromTracking(WorkoutTrackingParams workoutTrackingParams);

    FoodTrackingDTO updateFoodTracking(FoodTrackingParams foodTrackingParams);

    CommonResponseDTO removeFoodFromTracking(FoodTrackingParams foodTrackingParams);


   List<RecipeVariantDTO> getLatestFoodTracking(UUID userId);
}
