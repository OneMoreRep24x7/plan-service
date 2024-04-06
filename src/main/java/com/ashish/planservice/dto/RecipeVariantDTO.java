package com.ashish.planservice.dto;

import com.ashish.planservice.model.Recipe;
import com.ashish.planservice.model.RecipeVariant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RecipeVariantDTO {
    private Long id;
    private String unit;
    private Double quantity;
    private Double calories;
    private Double protein;
    private Double fat;
    private Double carbs;
    private Double fiber;
    private String recipeName; // Additional field for Recipe name

    // Constructor, getters, and setters
}
