package com.ashish.planservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class DailyDiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String day;
    private UUID ownerId;

    @ManyToMany
    @JoinTable(
            name = "daily_diet_breakfast",
            joinColumns = @JoinColumn(name = "daily_diet_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_variant_id")
    )
    private List<RecipeVariant> breakfast;

    @ManyToMany
    @JoinTable(
            name = "daily_diet_lunch",
            joinColumns = @JoinColumn(name = "daily_diet_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_variant_id")
    )
    private List<RecipeVariant> lunch;

    @ManyToMany
    @JoinTable(
            name = "daily_diet_dinner",
            joinColumns = @JoinColumn(name = "daily_diet_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_variant_id")
    )
    private List<RecipeVariant> dinner;
}
