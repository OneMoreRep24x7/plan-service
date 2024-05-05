package com.ashish.planservice.service;

import com.ashish.planservice.dto.RequestWorkout;
import com.ashish.planservice.dto.ResponseWorkout;
import com.ashish.planservice.model.Workout;
import com.ashish.planservice.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class WorkoutServiceImp implements WorkoutService{
    @Autowired
    WorkoutRepository repository;
    @Autowired
    CloudinaryService cloudinaryService;
    @Override
    public ResponseWorkout addWorkout(RequestWorkout requestWorkout, MultipartFile video) {
        String name = requestWorkout.getName();
        Optional<Workout> optionalWorkout = repository.findByName(name);
        if(optionalWorkout.isPresent()){
            return ResponseWorkout.builder()
                    .message("Workout already existed")
                    .statusCode(HttpStatus.CONFLICT.value())
                    .build();
        }
        String folder = "Workout_Videos";
        Map data =  cloudinaryService.uploadVideo(video,folder);
        String videoUrl = (String) data.get("secure_url");
        String publicUrl =(String) data.get("public_id");
        Workout workout = Workout.builder()
                .name(requestWorkout.getName())
                .description(requestWorkout.getDescription())
                .workoutCategory(requestWorkout.getWorkoutCategory())
                .durationMinutes(requestWorkout.getDurationMinutes())
                .caloriesBurned(requestWorkout.getCaloriesBurned())
                .videoUrl(videoUrl)
                .videoPublicUrl(publicUrl)
                .build();
        Workout savedWorkout = repository.save(workout);
        return ResponseWorkout.builder()
                .workout(savedWorkout)
                .message("Workout added successfully..")
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    @Override
    public List<Workout> getAllWorkouts() {
        return repository.findAll();
    }
}
