package com.ashish.planservice.service;

import com.ashish.planservice.dto.RequestRecipe;
import com.ashish.planservice.dto.ResponseRecipe;
import com.ashish.planservice.model.Recipe;
import com.ashish.planservice.repository.RecipeRepository;
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
        Recipe recipe = Recipe.builder()
                .name(recipeReq.getName())
                .category(recipeReq.getCategory())
                .quantity(recipeReq.getQuantity())
                .protein(recipeReq.getProtein())
                .fat(recipeReq.getFat())
                .carbs(recipeReq.getCarbs())
                .fiber(recipeReq.getFiber())
                .imageUrl(imageUrl)
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
}
