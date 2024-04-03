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
    private String videoUrl;
    private Integer durationMinutes;
    private Integer caloriesBurned;
}
