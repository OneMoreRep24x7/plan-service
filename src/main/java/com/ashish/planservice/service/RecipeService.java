package com.ashish.planservice.service;

import com.ashish.planservice.dto.RecipeVariantDTO;
import com.ashish.planservice.dto.RequestRecipe;
import com.ashish.planservice.dto.ResponseRecipe;
import com.ashish.planservice.model.Recipe;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RecipeService {
    ResponseRecipe addRecipe(RequestRecipe requestRecipe, MultipartFile image);

    List<Recipe> getAllRecipes();

    Recipe getRecipeById(Long foodId);
}
