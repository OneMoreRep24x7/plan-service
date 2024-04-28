package com.ashish.planservice.dto;

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
public class DailyDietDTO {
    private Long id;
    private String day;
    private UUID ownerId;
    private List<RecipeVariantDTO> breakfast;
    private List<RecipeVariantDTO> lunch;
    private List<RecipeVariantDTO> dinner;
}
