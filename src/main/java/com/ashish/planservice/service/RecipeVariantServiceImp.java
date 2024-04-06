package com.ashish.planservice.service;

import com.ashish.planservice.dto.RecipeVariantDTO;
import com.ashish.planservice.dto.RequestRecipeVariant;
import com.ashish.planservice.dto.ResponseRecipeVariant;
import com.ashish.planservice.model.Recipe;
import com.ashish.planservice.model.RecipeVariant;
import com.ashish.planservice.repository.RecipeRepository;
import com.ashish.planservice.repository.RecipeVariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeVariantServiceImp implements RecipeVariantService{
    @Autowired
    private RecipeVariantRepository variantRepository;
    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public ResponseRecipeVariant addRecipeVariant(
            RequestRecipeVariant requestRecipeVariant,
            Long recipeId
    ) {

        Recipe recipe = recipeRepository.findById(recipeId).get();
        RecipeVariant variant = RecipeVariant.builder()
                .recipe(recipe)
                .unit(requestRecipeVariant.getUnit())
                .quantity(requestRecipeVariant.getQuantity())
                .calories(requestRecipeVariant.getCalories())
                .protein(requestRecipeVariant.getProtein())
                .carbs(requestRecipeVariant.getCarbs())
                .fat(requestRecipeVariant.getFat())
                .fiber(requestRecipeVariant.getFiber())
                .build();
        RecipeVariant savedVariant = variantRepository.save(variant);

        return ResponseRecipeVariant.builder()
                .recipeVariant(savedVariant)
                .message("Variant created successfully..")
                .statusCode(HttpStatus.OK.value())
                .build();
    }


    @Override
    public List<RecipeVariantDTO> allVariants() {
        List<RecipeVariantDTO> variants = variantRepository.findAllVariantWithRecipes();

        return variants;
    }


}
