package com.ashish.planservice.dto;

import com.ashish.planservice.model.RecipeVariant;
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
public class DailyDietReqParams {
    private String day;
    private UUID ownerId;
    private List<RecipeVariant> breakfast;
    private List<RecipeVariant> lunch;
    private List<RecipeVariant> dinner;

}
