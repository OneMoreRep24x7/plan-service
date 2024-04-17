package com.ashish.planservice.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
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
@Data
@Builder
@Entity
public class WorkoutPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate date;
    private String planName;
    private int week;
    private UUID trainerId;
    private UUID userId;
    @JsonManagedReference
    @OneToMany(mappedBy = "workoutPlan")
    private List<DailyWorkout> dailyWorkouts;

}
