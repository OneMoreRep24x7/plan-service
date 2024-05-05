package com.ashish.planservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class DailyWorkout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String day;
    private String workoutType;
    private Double duration;
    private Double caloriesBurned;
    private UUID ownerId;

    @ManyToMany
    @JoinTable(
            name = "daily_workout_workouts",
            joinColumns = @JoinColumn(name = "daily_workout_id"),
            inverseJoinColumns = @JoinColumn(name = "workout_id")
    )
    private List<Workout> workouts;

    private boolean completed;

}

