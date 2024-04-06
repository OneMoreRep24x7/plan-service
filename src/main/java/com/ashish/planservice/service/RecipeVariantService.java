package com.ashish.planservice.service;


import com.ashish.planservice.dto.RecipeVariantDTO;
import com.ashish.planservice.dto.RequestRecipeVariant;
import com.ashish.planservice.dto.ResponseRecipeVariant;
import com.ashish.planservice.model.RecipeVariant;

import java.util.List;

public interface RecipeVariantService {
    ResponseRecipeVariant addRecipeVariant(RequestRecipeVariant requestRecipeVariant,Long recipeId);

    List<RecipeVariantDTO> allVariants();
}
