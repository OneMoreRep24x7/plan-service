package com.ashish.planservice.controller;

import com.ashish.planservice.dto.*;
import com.ashish.planservice.model.DailyDiet;
import com.ashish.planservice.model.DietPlan;
import com.ashish.planservice.model.WorkoutPlan;
import com.ashish.planservice.service.DietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/plans")
public class DietController {
    @Autowired
    private DietService dietService;

    @PostMapping("/addDailyDiet")
    public ResponseEntity<CommonResponseDTO> addDailyDiet(
            @RequestBody DailyDietReqParams dailyDietReqParams
            ){
        System.out.println(dailyDietReqParams+"Request of DailyDiet");
        return ResponseEntity.ok(dietService.addDailyDiet(dailyDietReqParams));
    }
    @GetMapping("/getDailyDiet")
    public ResponseEntity<List<DailyDietDTO>> getDailyDiet(
            @RequestParam("ownerId")UUID ownerId
            ){
        return ResponseEntity.ok(dietService.getDailyDiet(ownerId));
    }

    @GetMapping("/getAllDailyDiet")
    public ResponseEntity<List<DailyDietDTO>> getAllDailyDiet(){
        return ResponseEntity.ok(dietService.getAllDailyDiet());
    }

    @PostMapping("/addDietPlan")
    public ResponseEntity<CommonResponseDTO> addDietPlan(
            @RequestBody DietPlanParams dietPlanParams
            ){
        return ResponseEntity.ok(dietService.addDietPlan(dietPlanParams));
    }

    @PostMapping("/getTrainerDietPlan")
    public ResponseEntity<List<DietPlan>> getTrainerDietPlans(
            @RequestBody PlanReqParams planReqParams
    ){
        return  ResponseEntity.ok(dietService.getTrainerDietPlans(planReqParams));
    }
    @PostMapping("/getDietPlan")
    public ResponseEntity<DietPlanDTO> getDietPlan(
            @RequestBody WorkoutPlanGetParams planGetParams
    ){
        System.out.println(planGetParams+">>>>getPlans");
        return ResponseEntity.ok(dietService.getDietPlan(planGetParams));
    }


}
