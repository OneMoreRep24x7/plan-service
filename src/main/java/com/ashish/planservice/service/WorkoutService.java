package com.ashish.planservice.service;

import com.ashish.planservice.dto.RequestWorkout;
import com.ashish.planservice.dto.ResponseRecipe;
import com.ashish.planservice.dto.ResponseWorkout;
import com.ashish.planservice.model.Workout;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface WorkoutService {
    ResponseWorkout addWorkout(RequestWorkout requestWorkout, MultipartFile video);


    List<Workout> getAllWorkouts();
}
