package com.ashish.planservice.service;

import com.ashish.planservice.dto.RecipeVariantDTO;
import com.ashish.planservice.dto.RequestRecipe;
import com.ashish.planservice.dto.ResponseRecipe;
import com.ashish.planservice.model.Recipe;
import com.ashish.planservice.repository.RecipeRepository;
import com.ashish.planservice.repository.RecipeVariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RecipeServiceImp implements RecipeService{
    @Autowired
    RecipeRepository repository;
    @Autowired
    CloudinaryService cloudinaryService;

    @Override
    public ResponseRecipe addRecipe(RequestRecipe recipeReq , MultipartFile img) {
        String name = recipeReq.getName();
        Optional<Recipe> optionalRecipe = repository.findByName(name);
        if(optionalRecipe.isPresent()){
            return ResponseRecipe.builder()
                    .message("Recipe already exist")
                    .statusCode(HttpStatus.CONFLICT.value())
                    .build();
        }
        String folder = "Recipes_images";
        Map data =  cloudinaryService.upload(img,folder);
        String imageUrl = (String) data.get("secure_url");
        String publicId = (String) data.get("public_id");
        Recipe recipe = Recipe.builder()
                .name(recipeReq.getName())
                .description(recipeReq.getDescription())
                .category(recipeReq.getCategory())
                .imageUrl(imageUrl)
                .imagePublicId(publicId)
                .build();
        Recipe savedRecipe = repository.save(recipe);
        return ResponseRecipe.builder()
                .recipe(savedRecipe)
                .message("Recipe Created successfully")
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return repository.findAll();
    }

    @Override
    public Recipe getRecipeById(Long foodId) {

        Recipe recipe = repository.findById(foodId).get();
        return recipe;
    }

}
