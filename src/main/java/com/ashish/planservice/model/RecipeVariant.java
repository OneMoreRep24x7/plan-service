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
@Entity
@Builder
public class RecipeVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference

    @ManyToOne(fetch = FetchType.EAGER)
    private Recipe recipe;


    private String unit;
    private Double quantity;
    private Double calories;
    private Double protein;
    private Double fat;
    private Double carbs;
    private Double fiber;
}
