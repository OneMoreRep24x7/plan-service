package com.ashish.planservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String workoutCategory;
    private String videoUrl;
    private String videoPublicUrl;
    private Double durationMinutes;
    private Double caloriesBurned;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "daily_workout_id") // Assuming this is the foreign key column
    private DailyWorkout dailyWorkout;
}
