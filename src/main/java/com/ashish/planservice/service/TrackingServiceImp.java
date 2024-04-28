package com.ashish.planservice.service;

import com.ashish.planservice.configuration.AuthorityProxy;
import com.ashish.planservice.dto.*;
import com.ashish.planservice.model.RecipeVariant;
import com.ashish.planservice.model.Tracking;
import com.ashish.planservice.model.Workout;
import com.ashish.planservice.repository.RecipeVariantRepository;
import com.ashish.planservice.repository.TrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TrackingServiceImp implements TrackingService{
    @Autowired
    private TrackingRepository trackingRepository;

    @Autowired
    private RecipeVariantRepository recipeVariantRepository;
    @Autowired
    private AuthorityProxy authorityProxy;

    @Override
    public WorkoutTrackingDTO updateWorkoutTracking(WorkoutTrackingParams workoutTrackingParams) {
        UUID userId = workoutTrackingParams.getUserId();
        LocalDate currentDate = LocalDate.now();

        // Find the most recent tracking record for the user
        Optional<Tracking> optionalTracking = trackingRepository.findFirstByUserIdOrderByTrackingDateDesc(userId);

        // If the most recent tracking is today, update it; otherwise, create a new tracking
        Tracking tracking = optionalTracking
                .filter(t -> currentDate.equals(t.getTrackingDate()))
                .map(t -> {
                    t.getWorkouts().add(workoutTrackingParams.getWorkout());
                    return t;
                })
                .orElseGet(() -> {
                    Tracking newTracking = new Tracking();
                    newTracking.setUserId(userId);
                    newTracking.setTrackingDate(currentDate);
                    newTracking.setWorkouts(List.of(workoutTrackingParams.getWorkout()));
                    return newTracking;
                });

        // Update calories burned
        updateCaloriesBurned(userId, workoutTrackingParams.getWorkout().getCaloriesBurned());

        // Save the tracking record and return a response
        Tracking savedTracking = trackingRepository.save(tracking);

        return WorkoutTrackingDTO.builder()
                .details(savedTracking)
                .message("Workout added successfully!")
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    private void updateCaloriesBurned(UUID userId, Double caloriesBurned) {
        CaloriesBurnReqDTO caloriesBurnReqDTO = CaloriesBurnReqDTO.builder()
                .userId(userId)
                .caloriesBurned(caloriesBurned)
                .build();
        authorityProxy.updateCaloriesBurned(caloriesBurnReqDTO);
    }


    @Override
    public WorkoutTrackingDTO getLatestWorkoutTracking(UUID userId) {
        Optional<Tracking> optionalTracking = trackingRepository.findFirstByUserIdOrderByTrackingDateDesc(userId);
        if(optionalTracking.isPresent()){
            Tracking tracking = optionalTracking.get();
            return WorkoutTrackingDTO.builder()
                    .details(tracking)
                    .message("Tracked workout fetched successfully")
                    .statusCode(HttpStatus.OK.value())
                    .build();
        }
        return WorkoutTrackingDTO.builder()
                .message("No Tracked workouts Yet")
                .statusCode(HttpStatus.NO_CONTENT.value()) //204
                .build();
    }

    @Override
    public CommonResponseDTO removeWorkoutFromTracking(WorkoutTrackingParams workoutTrackingParams) {
        UUID userId = workoutTrackingParams.getUserId();
        Workout workoutToRemove = workoutTrackingParams.getWorkout();
        Optional<Tracking> optionalTracking = trackingRepository.findFirstByUserIdOrderByTrackingDateDesc(userId);
        if(optionalTracking.isPresent()){
            Tracking tracking = optionalTracking.get();
            List<Workout> workouts = tracking.getWorkouts();
            workouts.removeIf(workout -> workout.getId().equals(workoutToRemove.getId()));
            tracking.setWorkouts(workouts);
            updateCaloriesBurned(userId,0-workoutToRemove.getCaloriesBurned());
            trackingRepository.save(tracking);
            return CommonResponseDTO.builder()
                    .message("Workout removed from tracking")
                    .statusCode(HttpStatus.OK.value())
                    .build();

        }
        return CommonResponseDTO.builder()
                .message("Tracking not yet started!")
                .statusCode(HttpStatus.NO_CONTENT.value())
                .build();

    }

    @Override
    public FoodTrackingDTO updateFoodTracking(FoodTrackingParams foodTrackingParams) {
        UUID userId = foodTrackingParams.getUserId();
        Long variantId = foodTrackingParams.getVariantId();
        LocalDate today = LocalDate.now();
        Optional<Tracking> optionalTracking = trackingRepository.findFirstByUserIdOrderByTrackingDateDesc(userId);
        Optional<RecipeVariant> optionalVariant = recipeVariantRepository.findById(variantId);
        Tracking tracking;
        if (optionalTracking.isPresent()) {
            tracking = optionalTracking.get();
            if(today.equals(tracking.getTrackingDate())){
                RecipeVariant variant = optionalVariant.get();
                tracking.getRecipeVariants().add(variant);
                CaloriesEatenReqDTO caloriesEaten =CaloriesEatenReqDTO.builder()
                        .userId(userId)
                        .caloriesEaten(variant.getCalories())
                        .build();
                authorityProxy.updateCaloriesEaten(caloriesEaten);
                trackingRepository.save(tracking);
                return FoodTrackingDTO.builder()
                        .foodName(variant.getRecipe().getName())
                        .details(tracking)
                        .message("Food variant tracked successfully")
                        .statusCode(HttpStatus.OK.value())
                        .build();
            }else {
                tracking = Tracking.builder()
                        .userId(userId)
                        .trackingDate(today)
                        .recipeVariants(new ArrayList<>())
                        .build();
            }
        } else {
            tracking = Tracking.builder()
                    .userId(userId)
                    .trackingDate(today)
                    .recipeVariants(new ArrayList<>())
                    .build();
            CaloriesEatenReqDTO caloriesEaten =CaloriesEatenReqDTO.builder()
                    .userId(userId)
                    .caloriesEaten(0)
                    .build();
            authorityProxy.updateCaloriesEaten(caloriesEaten);
            trackingRepository.save(tracking);
        }
        return null;
    }

    @Override
    public CommonResponseDTO removeFoodFromTracking(FoodTrackingParams foodTrackingParams) {
        UUID userId = foodTrackingParams.getUserId();
        Long variantId = foodTrackingParams.getVariantId();
        LocalDate today = LocalDate.now();
        Optional<Tracking> optionalTracking = trackingRepository.findFirstByUserIdOrderByTrackingDateDesc(userId);

        if (optionalTracking.isEmpty()) {
            return CommonResponseDTO.builder()
                    .message("No tracking record found for today")
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .build();
        }

        Tracking tracking = optionalTracking.get();

        // Retrieve the list of recipe variants in the tracking record
        List<RecipeVariant> recipeVariants = tracking.getRecipeVariants();
        RecipeVariant recipeVariant = recipeVariantRepository.findById(variantId).get();

        // Find the first occurrence of the specified variant
        Optional<RecipeVariant> variantToRemove = recipeVariants.stream()
                .filter(v -> v.getId().equals(variantId))
                .findFirst();

        if (variantToRemove.isEmpty()) {
            return CommonResponseDTO.builder()
                    .message("Specified variant not found in today's tracking")
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .build();
        }

        // Remove the variant from the list
        recipeVariants.remove(variantToRemove.get());
        CaloriesEatenReqDTO caloriesEaten =CaloriesEatenReqDTO.builder()
                .userId(userId)
                .caloriesEaten(0-recipeVariant.getCalories())
                .build();
        authorityProxy.updateCaloriesEaten(caloriesEaten);

        trackingRepository.save(tracking);


        return CommonResponseDTO.builder()
                .message("Food variant removed from today's tracking")
                .statusCode(HttpStatus.OK.value())
                .build();
    }



    @Override
    public List<RecipeVariantDTO> getLatestFoodTracking(UUID userId) {

        Optional<Tracking> optionalTracking = trackingRepository.findFirstByUserIdOrderByTrackingDateDesc(userId);

        if (optionalTracking.isPresent()) {
            Tracking tracking = optionalTracking.get();
            List<RecipeVariantDTO> recipeVariantDTOs = tracking.getRecipeVariants().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return recipeVariantDTOs;
        }

        return List.of();
    }

    private RecipeVariantDTO convertToDTO(RecipeVariant recipeVariant) {
        return RecipeVariantDTO.builder()
                .id(recipeVariant.getId())
                .unit(recipeVariant.getUnit())
                .quantity(recipeVariant.getQuantity())
                .calories(recipeVariant.getCalories())
                .protein(recipeVariant.getProtein())
                .fat(recipeVariant.getFat())
                .carbs(recipeVariant.getCarbs())
                .fiber(recipeVariant.getFiber())
                .recipeName(recipeVariant.getRecipe().getName())
                .build();
    }

}
