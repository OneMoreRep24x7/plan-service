package com.ashish.planservice.service;

import com.ashish.planservice.configuration.NotificationProxy;
import com.ashish.planservice.dto.*;
import com.ashish.planservice.kafka.KafkaMessagePublisher;
import com.ashish.planservice.model.*;
import com.ashish.planservice.repository.DailyDietRepository;
import com.ashish.planservice.repository.DietPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DietServiceImp implements DietService{
    @Autowired
    private DailyDietRepository dailyDietRepository;
    @Autowired
    private DietPlanRepository dietPlanRepository;
    @Autowired
    private KafkaMessagePublisher kafkaMessagePublisher;
    @Autowired
    private NotificationProxy notificationProxy;

    @Override
    public CommonResponseDTO addDailyDiet(DailyDietReqParams dailyDietReqParams) {
        DailyDiet dailyDiet = DailyDiet.builder()
                .day(dailyDietReqParams.getDay())
                .ownerId(dailyDietReqParams.getOwnerId())
                .breakfast(dailyDietReqParams.getBreakfast())
                .lunch(dailyDietReqParams.getLunch())
                .dinner(dailyDietReqParams.getDinner())
                .build();
            dailyDietRepository.save(dailyDiet);
        return CommonResponseDTO.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Daily diet added successfully...")
                .build();
    }

    @Override
    public List<DailyDietDTO> getDailyDiet(UUID ownerId) {
        Optional<List<DailyDiet>> optionalDailyDiets = dailyDietRepository.findByOwnerId(ownerId);

        if (optionalDailyDiets.isPresent()) {
            List<DailyDiet> dailyDiets = optionalDailyDiets.get();

            // Convert each DailyDiet to DailyDietDTO
            return dailyDiets.stream()
                    .map(this::convertToDailyDietDTO)  // Convert to DTO with recipe names
                    .collect(Collectors.toList());
        }

        return List.of();  // Return an empty list if there's no data
    }

    @Override
    public List<DailyDietDTO> getAllDailyDiet() {
        List<DailyDiet> dailyDiets = dailyDietRepository.findAll();

        if (dailyDiets == null || dailyDiets.isEmpty()) {
            return Collections.emptyList();  // Return an empty list if no data
        }

        // Convert the list of DailyDiet to DailyDietDTO
        return dailyDiets.stream()
                .map(this::convertToDailyDietDTO)  // Convert to DTO
                .collect(Collectors.toList());
    }

    @Override
    public CommonResponseDTO addDietPlan(DietPlanParams dietPlanParams) {
        UUID userId = dietPlanParams.getUserId();
        LocalDate startDate = dietPlanParams.getStartDate();
        int repeat = dietPlanParams.getRepeat();
        LocalDate planExpire = startDate.plusWeeks(repeat);
        int month = startDate.getMonthValue();
        Optional<List<DietPlan>> optionalDietPlans = dietPlanRepository.findByUserId(userId);
        if (optionalDietPlans.isPresent()) {
            List<DietPlan> dietPlans = optionalDietPlans.get();
            for (DietPlan plan : dietPlans) {
                if (LocalDate.now().isBefore(plan.getPlanExpire())) {
                    return CommonResponseDTO.builder()
                            .message("Workout plan already exists for this week in the same month.")
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build();
                }
            }
        }

        DietPlan dietPlan = DietPlan.builder()
                .planName(dietPlanParams.getPlanName())
                .startDate(dietPlanParams.getStartDate())
                .repeat(dietPlanParams.getRepeat())
                .trainerId(dietPlanParams.getTrainerId())
                .dailyDiets(dietPlanParams.getDailyDiets())
                .userId(dietPlanParams.getUserId())
                .planExpire(planExpire)
                .build();

        DietPlan savedDietPlan = dietPlanRepository.save(dietPlan);
        MsgDTO message = MsgDTO.builder()
                .userFullName(dietPlanParams.getUserFullName())
                .trainerFullName(dietPlanParams.getTrainerFullName())
                .planName(dietPlanParams.getPlanName())
                .planStart(savedDietPlan.getStartDate())
                .planExpire(savedDietPlan.getPlanExpire())
                .userEmail(dietPlanParams.getUserEmail())
                .phoneNumber(dietPlanParams.getUserPhoneNumber())
                .build();
//        kafkaMessagePublisher.sendEventsToTopic(message);
        notificationProxy.sendNotification(message);
        return CommonResponseDTO.builder()
                .message("Workout plan added successfully")
                .statusCode(HttpStatus.OK.value())
                .build();

    }

    @Override
    public List<DietPlan> getTrainerDietPlans(PlanReqParams planReqParams) {
        UUID userId = planReqParams.getUserId();
        UUID trainerId = planReqParams.getTrainerId();

        Optional<List<DietPlan>> optionalDietPlans = dietPlanRepository.findByUserIdAndTrainerId(userId, trainerId);
        if(optionalDietPlans.isPresent()){
            List<DietPlan> dietPlans = optionalDietPlans.get();
            return dietPlans;
        }

        return null;
    }

    @Override
    public DietPlanDTO getDietPlan(WorkoutPlanGetParams planGetParams) {
        UUID userID = planGetParams.getUserId();
        UUID trainerId = planGetParams.getTrainerId();

        LocalDate currentDate = planGetParams.getDate();

        // Verify if the day is correct
        DayOfWeek dayOfWeek = currentDate.getDayOfWeek();


        Optional<List<DietPlan>> optionalDietPlans = dietPlanRepository.findByUserIdAndTrainerId(userID, trainerId);
        if (optionalDietPlans.isPresent()) {
            List<DietPlan> dietPlans= optionalDietPlans.get();
            for (DietPlan dietPlan : dietPlans) {
                LocalDate startDate = dietPlan.getStartDate();
                LocalDate endDate = dietPlan.getPlanExpire();

                // Validate start and end dates for correctness
                System.out.println("Workout start date: " + startDate + " end date: " + endDate);

                if (!currentDate.isBefore(startDate) && !currentDate.isAfter(endDate)) {
                    List<DailyDiet> plansDailyDiets = dietPlan.getDailyDiets();

                    for (DailyDiet dailyDiet : plansDailyDiets) {
                        if (dailyDiet.getDay().equals(dayOfWeek.toString())) {
                            DailyDietDTO dailyDietDTO = convertToDailyDietDTO(dailyDiet);
                            return DietPlanDTO.builder()
                                    .todayDiet(dailyDietDTO)
                                    .dietPlan(dietPlan)
                                    .message("Today's diet is fetched successfully.")
                                    .statusCode(HttpStatus.OK.value())
                                    .build();
                        }
                    }
                }
            }
        }

        return DietPlanDTO.builder()
                .message("Diet is not yet added by the trainer.")
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
    }

    @Override
    public CommonResponseDTO deleteDietPlan(DeleteReqDTO deleteReqDTO) {
        UUID userId = deleteReqDTO.getUserId();
        UUID trainerId = deleteReqDTO.getTrainerId();
        dietPlanRepository.deleteByUserIdAndTrainerId(userId,trainerId);
        return CommonResponseDTO.builder()
                .message("Plan Deleted successfully")
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    @Override
    public CommonResponseDTO deleteDailyDiet(Long planId) {
        dailyDietRepository.deleteById(planId);
        return CommonResponseDTO.builder()
                .message("Daily Diet Plan Deleted successfully")
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    @Override
    public CommonResponseDTO updateDietPlan(UpdateDietPlanReq updateDietPlanReq) {
        Long dietPlanId = updateDietPlanReq.getDietPlanId();
        Optional<DietPlan> optionalPlan = dietPlanRepository.findById(dietPlanId);

        if (!optionalPlan.isPresent()) {
            return CommonResponseDTO.builder()
                    .message("Diet plan not found.")
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .build();
        }

        DietPlan existingPlan = optionalPlan.get();

        existingPlan.setPlanName(updateDietPlanReq.getPlanName());
        existingPlan.setStartDate(updateDietPlanReq.getStartDate());
        existingPlan.setRepeat(updateDietPlanReq.getRepeat());
        existingPlan.setTrainerId(updateDietPlanReq.getTrainerId());
        existingPlan.setDailyDiets(updateDietPlanReq.getDailyDiets());

        LocalDate planExpire = updateDietPlanReq.getStartDate().plusWeeks(updateDietPlanReq.getRepeat());
        existingPlan.setPlanExpire(planExpire);

        dietPlanRepository.save(existingPlan);

        return CommonResponseDTO.builder()
                .message("Diet plan updated successfully.")
                .statusCode(HttpStatus.OK.value())
                .build();

    }

    @Override
    public CommonResponseDTO updateDailyDiet(UpdateDailyDietReq updateDailyDietReq) {
        Long dailyDietId = updateDailyDietReq.getDailyDietId();
        Optional<DailyDiet> optionalDiet = dailyDietRepository.findById(dailyDietId);

        if (!optionalDiet.isPresent()) {
            return CommonResponseDTO.builder()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .message("Daily diet not found.")
                    .build();
        }

        DailyDiet existingDiet = optionalDiet.get();

        existingDiet.setDay(updateDailyDietReq.getDay());
        existingDiet.setOwnerId(updateDailyDietReq.getOwnerId());
        existingDiet.setBreakfast(updateDailyDietReq.getBreakfast());
        existingDiet.setLunch(updateDailyDietReq.getLunch());
        existingDiet.setDinner(updateDailyDietReq.getDinner());

        dailyDietRepository.save(existingDiet);

        return CommonResponseDTO.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Daily diet updated successfully.")
                .build();

    }


    public DailyDietDTO convertToDailyDietDTO(DailyDiet dailyDiet) {
        return DailyDietDTO.builder()
                .id(dailyDiet.getId())
                .day(dailyDiet.getDay())
                .ownerId(dailyDiet.getOwnerId())
                .breakfast(dailyDiet.getBreakfast().stream().map(this::convertToDTO).collect(Collectors.toList()))
                .lunch(dailyDiet.getLunch().stream().map(this::convertToDTO).collect(Collectors.toList()))
                .dinner(dailyDiet.getDinner().stream().map(this::convertToDTO).collect(Collectors.toList()))
                .build();
    }

    private RecipeVariantDTO convertToDTO(RecipeVariant recipeVariant) {
        return RecipeVariantDTO.builder()
                .id(recipeVariant.getId())
                .unit(recipeVariant.getUnit())
                .quantity(recipeVariant.getQuantity())
                .calories(recipeVariant.getCalories())
                .protein(recipeVariant.getProtein())
                .fat(recipeVariant.getFat())
                .carbs(recipeVariant.getCarbs())
                .fiber(recipeVariant.getFiber())
                .recipeName(recipeVariant.getRecipe().getName())
                .build();
    }

}
