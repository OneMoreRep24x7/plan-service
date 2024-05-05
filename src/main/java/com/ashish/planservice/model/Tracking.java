package com.ashish.planservice.model;

import jakarta.persistence.*;
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
@Entity
public class Tracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID userId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tracking_workouts",
            joinColumns = @JoinColumn(name = "tracking_id"),
            inverseJoinColumns = @JoinColumn(name = "workout_id")
    )
    private List<Workout> workouts;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tracking_foods",
            joinColumns = @JoinColumn(name = "tracking_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_variant_id")
    )
    private List<RecipeVariant> recipeVariants;

    private LocalDate trackingDate;

}
