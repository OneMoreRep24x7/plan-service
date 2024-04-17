package com.ashish.planservice.dto;

import com.ashish.planservice.model.DailyWorkout;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class WorkoutPlanParams {
    private LocalDate date;
    private String planName;
    private UUID trainerId;
    private UUID userID;
    private int week;
    private List<DailyWorkout> dailyWorkouts;
}
