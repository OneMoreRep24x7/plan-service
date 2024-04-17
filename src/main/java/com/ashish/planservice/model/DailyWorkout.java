package com.ashish.planservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
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

    @JsonManagedReference
    @OneToMany(mappedBy = "dailyWorkout", fetch = FetchType.EAGER)
    private List<Workout> workouts=new ArrayList<>();
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "workout_plan_id")
    private WorkoutPlan workoutPlan;
    private boolean completed;

}

