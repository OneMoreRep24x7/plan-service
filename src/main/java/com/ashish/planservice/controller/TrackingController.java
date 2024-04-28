package com.ashish.planservice.controller;

import com.ashish.planservice.dto.*;
import com.ashish.planservice.service.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/plans")
public class TrackingController {
    @Autowired
    private TrackingService trackingService;

    @PostMapping("/updateWorkoutTracking")
    public ResponseEntity<WorkoutTrackingDTO> updateWorkoutTracking(
            @RequestBody WorkoutTrackingParams workoutTrackingParams
            ){
        return ResponseEntity.ok(trackingService.updateWorkoutTracking(workoutTrackingParams));
    }

    @GetMapping("/getLatestWorkoutTracking")
    public ResponseEntity<WorkoutTrackingDTO> getLatestWorkoutTracking(
            @RequestParam UUID userId
            ){
        return ResponseEntity.ok(trackingService.getLatestWorkoutTracking(userId));
    }

    @PostMapping("/removeWorkoutFromTracking")
    public ResponseEntity<CommonResponseDTO> removeWorkoutFromTracking(
            @RequestBody WorkoutTrackingParams workoutTrackingParams
    ){
        System.out.println(workoutTrackingParams+">>>>TrackingParams");
        return ResponseEntity.ok(trackingService.removeWorkoutFromTracking(workoutTrackingParams));
    }

    @PostMapping("/updateFoodTracking")
    public ResponseEntity<FoodTrackingDTO> updateFoodTracking(
            @RequestBody FoodTrackingParams foodTrackingParams
    ){
        return ResponseEntity.ok(trackingService.updateFoodTracking(foodTrackingParams));
    }

    @PostMapping("/removeFoodFromTracking")
    public ResponseEntity<CommonResponseDTO> removeFoodFromTracking(
            @RequestBody FoodTrackingParams foodTrackingParams
    ){
        return ResponseEntity.ok(trackingService.removeFoodFromTracking(foodTrackingParams));
    }

    @GetMapping("/getLatestFoodTracking")
    public  ResponseEntity<List<RecipeVariantDTO>> getLatestFoodTracking(
            @RequestParam UUID userId
    ){
        return ResponseEntity.ok(trackingService.getLatestFoodTracking(userId));
    }


}
