package com.ashish.planservice.controller;

import com.ashish.planservice.dto.RequestRecipe;
import com.ashish.planservice.dto.ResponseRecipe;
import com.ashish.planservice.model.Recipe;
import com.ashish.planservice.service.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recipe")
public class RecipeController {
    @Autowired
    RecipeService recipeService;

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
}
