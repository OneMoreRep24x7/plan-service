package com.ashish.planservice.controller;

import com.ashish.planservice.dto.*;
import com.ashish.planservice.model.DailyWorkout;
import com.ashish.planservice.model.Workout;
import com.ashish.planservice.model.WorkoutPlan;
import com.ashish.planservice.service.PlanService;
import com.ashish.planservice.service.WorkoutService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/plans")
public class WorkoutController {
    @Autowired
    private WorkoutService workoutService;
    @Autowired
    private PlanService planService;
    @PostMapping("/addWorkout")
    public ResponseEntity<ResponseWorkout> addWorkout(
            @RequestPart("file") MultipartFile file,
            @RequestParam("workoutRequest") String workoutRequest
    ) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        RequestWorkout requestWorkout = objectMapper.readValue(workoutRequest,RequestWorkout.class);
        return ResponseEntity.ok(workoutService.addWorkout(requestWorkout,file));
    }

    @GetMapping("/getWorkouts")
    public ResponseEntity<List<Workout>> getWorkouts(){
        return ResponseEntity.ok(workoutService.getAllWorkouts());
    }

    @PostMapping("/addDailyWorkout")
    public ResponseEntity<CommonResponseDTO> addDailyWorkout(
            @RequestBody DailyWorkoutParams dailyWorkoutParams
    ){
        return ResponseEntity.ok(planService.addDailyWorkout(dailyWorkoutParams));
    }

    @GetMapping("/getDailyWorkouts")
    public ResponseEntity<List<DailyWorkout>> getDailyWorkout(
            @RequestParam("ownerId") UUID ownerId
            ){
        return ResponseEntity.ok(planService.getDailyWorkouts(ownerId));
    }

    @PostMapping("/addWorkoutPlan")
    public ResponseEntity<CommonResponseDTO> addWorkoutPlan(
            @RequestBody WorkoutPlanParams workoutPlanParams
    ){
        return ResponseEntity.ok(planService.addWorkoutPlan(workoutPlanParams));
    }

    @PostMapping("/getWorkoutPlan")
    public ResponseEntity<WorkoutPlan> getWorkoutPlan(
            @RequestBody WorkoutPlanGetParams planGetParams
    ){
        return ResponseEntity.ok(planService.getWorkoutPlan(planGetParams));
    }


}
