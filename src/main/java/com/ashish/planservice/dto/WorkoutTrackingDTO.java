package com.ashish.planservice.dto;

import com.ashish.planservice.model.Tracking;
import com.ashish.planservice.model.Workout;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class WorkoutTrackingDTO {
    private Tracking details;
    private String message;
    private int statusCode;
}
