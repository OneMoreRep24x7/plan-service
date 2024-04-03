package com.ashish.planservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RequestRecipe {
    private String name;
    private String category;
    private Double quantity;
    private Double protein;
    private Double fat;
    private Double carbs;
    private Double fiber;
    private String imageUrl;
}
