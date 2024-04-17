package com.ashish.planservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class WorkoutPlanGetParams {
    private int week;
    private LocalDate date;
    private UUID userId;
}
