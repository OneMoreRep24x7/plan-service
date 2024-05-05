package com.ashish.planservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RequestWorkout {
    private String name;
    private String description;
    private String workoutCategory;
    private String videoUrl;
    private Double durationMinutes;
    private Double caloriesBurned;
}
