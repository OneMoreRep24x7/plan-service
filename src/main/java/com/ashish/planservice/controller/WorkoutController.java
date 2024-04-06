package com.ashish.planservice.controller;

import com.ashish.planservice.dto.RequestRecipe;
import com.ashish.planservice.dto.RequestWorkout;
import com.ashish.planservice.dto.ResponseRecipe;
import com.ashish.planservice.dto.ResponseWorkout;
import com.ashish.planservice.model.Workout;
import com.ashish.planservice.service.WorkoutService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/plans")
public class WorkoutController {
    @Autowired
    WorkoutService workoutService;

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
}
