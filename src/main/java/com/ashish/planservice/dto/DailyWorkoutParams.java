package com.ashish.planservice.dto;

import com.ashish.planservice.model.Workout;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DailyWorkoutParams {
    private UUID ownerId;
    private String day;
    private String workoutType;
    private List<Workout> workouts;
}
