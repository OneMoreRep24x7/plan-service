package com.ashish.planservice.dto;

import com.ashish.planservice.model.Workout;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseWorkout {
    private Workout workout;
    private String message;
    private int statusCode;
}
