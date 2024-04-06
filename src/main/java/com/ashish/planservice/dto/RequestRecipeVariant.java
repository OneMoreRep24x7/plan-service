package com.ashish.planservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RequestRecipeVariant {


    private String unit;
    private Double quantity;
    private Double calories;
    private Double protein;
    private Double fat;
    private Double carbs;
    private Double fiber;
}
