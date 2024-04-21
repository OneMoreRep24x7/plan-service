package com.ashish.planservice.dto;

import com.ashish.planservice.model.Workout;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class WorkoutTrackingParams {
    private UUID userId;
    private Workout workout;
}
