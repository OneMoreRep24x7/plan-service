package com.ashish.planservice.controller;

import com.ashish.planservice.dto.*;
import com.ashish.planservice.model.Recipe;
import com.ashish.planservice.model.RecipeVariant;
import com.ashish.planservice.service.RecipeService;
import com.ashish.planservice.service.RecipeVariantService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/plans")
public class RecipeController {
    @Autowired
    RecipeService recipeService;
    @Autowired
    RecipeVariantService recipeVariantService;

    @PostMapping("/addRecipe")
    public ResponseEntity<ResponseRecipe> addRecipe(
            @RequestPart("file") MultipartFile file,
            @RequestParam("recipeRequest") String recipeRequest
    ) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        RequestRecipe requestRecipe = objectMapper.readValue( recipeRequest,RequestRecipe.class);
        return ResponseEntity.ok(recipeService.addRecipe(requestRecipe,file));
    }

    @GetMapping("/getRecipes")
    public ResponseEntity<List<Recipe>> getRecipes(){
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    @GetMapping("/getRecipeVariant")
    public ResponseEntity<?> getVariant(){

        return ResponseEntity.ok(recipeVariantService.allVariants());
    }

    @PostMapping("/addRecipeVariant")
    public ResponseEntity<ResponseRecipeVariant> addRecipeVariant(
            @RequestParam("recipeId") Long recipeId,
            @RequestBody RequestRecipeVariant requestRecipeVariant
    ){
        System.out.println(requestRecipeVariant.getProtein()+">>>>>");
        return  ResponseEntity.ok(recipeVariantService.addRecipeVariant(requestRecipeVariant,recipeId));
    }

    @GetMapping("/getRecipeById")
    public ResponseEntity<Recipe> getRecipeById(
            @RequestParam("foodId") Long foodId){
        return ResponseEntity.ok(recipeService.getRecipeById(foodId));
    }


}
