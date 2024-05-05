package com.ashish.planservice.dto;

import com.ashish.planservice.model.DailyDiet;
import com.ashish.planservice.model.DailyWorkout;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DietPlanParams {
    private LocalDate startDate;
    private String userFullName;
    private String trainerFullName;
    private String userEmail;
    private String userPhoneNumber;
    private String planName;
    private UUID trainerId;
    private UUID userId;
    private int repeat;

    private List<DailyDiet> dailyDiets;
}
