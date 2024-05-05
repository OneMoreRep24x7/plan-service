package com.ashish.planservice.service;

import com.ashish.planservice.dto.*;
import com.ashish.planservice.model.DailyDiet;
import com.ashish.planservice.model.DietPlan;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DietService {
    CommonResponseDTO addDailyDiet(DailyDietReqParams dailyDietReqParams);

    List<DailyDietDTO> getDailyDiet(UUID ownerId);

    List<DailyDietDTO>  getAllDailyDiet();

    CommonResponseDTO addDietPlan(DietPlanParams dietPlanParams);

    List<DietPlan> getTrainerDietPlans(PlanReqParams planReqParams);

    DietPlanDTO getDietPlan(WorkoutPlanGetParams planGetParams);

    CommonResponseDTO deleteDietPlan(DeleteReqDTO deleteReqDTO);

    CommonResponseDTO deleteDailyDiet(Long planId);

    CommonResponseDTO updateDietPlan(UpdateDietPlanReq updateDietPlanReq);

    CommonResponseDTO updateDailyDiet(UpdateDailyDietReq updateDailyDietReq);
}
