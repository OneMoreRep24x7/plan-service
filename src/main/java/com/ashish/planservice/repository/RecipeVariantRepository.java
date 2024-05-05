package com.ashish.planservice.repository;

import com.ashish.planservice.dto.RecipeVariantDTO;
import com.ashish.planservice.model.Recipe;
import com.ashish.planservice.model.RecipeVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeVariantRepository extends JpaRepository<RecipeVariant,Long> {
    Optional<RecipeVariant> findByRecipe(Recipe recipe);

    @Query("SELECT new com.ashish.planservice.dto.RecipeVariantDTO(rv.id, rv.unit, rv.quantity, rv.calories, rv.protein, rv.fat, rv.carbs, rv.fiber, r.name) FROM RecipeVariant rv JOIN rv.recipe r")
    List<RecipeVariantDTO> findAllVariantWithRecipes();
    @Query("SELECT rv FROM RecipeVariant rv JOIN FETCH rv.recipe WHERE rv.id = :variantId")
    RecipeVariant fetchVariantWithRecipe(@Param("variantId") Long variantId);


}
