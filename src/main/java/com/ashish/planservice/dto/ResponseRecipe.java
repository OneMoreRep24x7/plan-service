package com.ashish.planservice.dto;

import com.ashish.planservice.model.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ResponseRecipe {
    private Recipe recipe;
    private String message;
    private int statusCode;
}
