package com.ashish.planservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class WorkoutPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate startDate;
    private String planName;
    @Column(name = "`repeat`") // Escaping the column name using backticks
    private int repeat;
    private UUID trainerId;
    private UUID userId;
    private LocalDate planExpire;

    @OneToMany()
    @JoinColumn(name = "workout_plan_id")
    private List<DailyWorkout> dailyWorkouts = new ArrayList<>();
}
