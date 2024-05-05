package com.ashish.planservice.dto;

import com.ashish.planservice.model.RecipeVariant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseRecipeVariant {
    private RecipeVariant recipeVariant;
    private String message;
    private int statusCode;
}
