package com.ashish.planservice.dto;

import com.ashish.planservice.model.DailyWorkout;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class WorkoutPlanDTO {
    private DailyWorkout todayWorkout;
    private String message;
    private int statusCode;
}
