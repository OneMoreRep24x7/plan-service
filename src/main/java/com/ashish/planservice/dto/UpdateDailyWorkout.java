package com.ashish.planservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateDailyWorkout extends DailyWorkoutParams {
    private Long dailyWorkoutId;
}
