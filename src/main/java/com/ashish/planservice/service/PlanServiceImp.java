package com.ashish.planservice.service;

import com.ashish.planservice.dto.*;
import com.ashish.planservice.model.DailyWorkout;
import com.ashish.planservice.model.Workout;
import com.ashish.planservice.model.WorkoutPlan;
import com.ashish.planservice.repository.DailyWorkoutRepository;
import com.ashish.planservice.repository.WorkoutPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PlanServiceImp implements PlanService{
    @Autowired
    private DailyWorkoutRepository dailyWorkoutRepository;
    @Autowired
    private WorkoutPlanRepository workoutPlanRepository;

    @Override
    public CommonResponseDTO addDailyWorkout(DailyWorkoutParams dailyWorkoutParams) {
        System.out.println(dailyWorkoutParams+">>>>>>>>>");
        List<Workout> workouts = dailyWorkoutParams.getWorkouts().stream().collect(Collectors.toList());

        double totalDuration = 0.0;
        double totalCaloriesBurned = 0.0;
        for (Workout workout : workouts) {
            totalDuration += workout.getDurationMinutes();
            totalCaloriesBurned += workout.getCaloriesBurned();


        }


        DailyWorkout dailyWorkout = new DailyWorkout();
        dailyWorkout.setDay(dailyWorkoutParams.getDay());
        dailyWorkout.setWorkoutType(dailyWorkoutParams.getWorkoutType());
        dailyWorkout.setWorkouts(dailyWorkoutParams.getWorkouts());
        dailyWorkout.setOwnerId(dailyWorkoutParams.getOwnerId());
        dailyWorkout.setDuration(totalDuration);
        dailyWorkout.setCaloriesBurned(totalCaloriesBurned);
        dailyWorkout.setWorkouts(workouts);
        DailyWorkout savedWorkout = dailyWorkoutRepository.save(dailyWorkout);
        System.out.println(savedWorkout);
        return CommonResponseDTO.builder()
                .message("Daily workout added successfully...")
                .statusCode(HttpStatus.OK.value())
                .build();

    }

    @Override
    public List<DailyWorkout> getDailyWorkouts(UUID ownerId) {
        Optional<List<DailyWorkout>> optionalDailyWorkouts = dailyWorkoutRepository.findByOwnerId(ownerId);
        if(optionalDailyWorkouts.isPresent()){
            List<DailyWorkout> dailyWorkouts = optionalDailyWorkouts.get();
            return  dailyWorkouts;
        }
        return null;
    }

    @Override
    public CommonResponseDTO addWorkoutPlan(WorkoutPlanParams workoutPlanParams) {

        UUID userId = workoutPlanParams.getUserId();
        LocalDate startDate = workoutPlanParams.getStartDate();
        int repeat = workoutPlanParams.getRepeat();
        LocalDate planExpire = startDate.plusWeeks(repeat);
        int month = startDate.getMonthValue();
        Optional<List<WorkoutPlan>> optionalWorkoutPlans = workoutPlanRepository.findByUserId(userId);
        if (optionalWorkoutPlans.isPresent()) {
            List<WorkoutPlan> workoutPlans = optionalWorkoutPlans.get();
            for (WorkoutPlan plan : workoutPlans) {
                if (LocalDate.now().isBefore(plan.getPlanExpire())) {
                    return CommonResponseDTO.builder()
                            .message("Workout plan already exists for this week in the same month.")
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build();
                }
            }
        }

        WorkoutPlan workoutPlan = WorkoutPlan.builder()
                .planName(workoutPlanParams.getPlanName())
                .startDate(workoutPlanParams.getStartDate())
                .repeat(workoutPlanParams.getRepeat())
                .trainerId(workoutPlanParams.getTrainerId())
                .dailyWorkouts(workoutPlanParams.getDailyWorkouts())
                .userId(workoutPlanParams.getUserId())
                .planExpire(planExpire)
                .build();

        workoutPlanRepository.save(workoutPlan);
        return CommonResponseDTO.builder()
                .message("Workout plan added successfully")
                .statusCode(HttpStatus.OK.value())
                .build();
    }


    @Override
    public WorkoutPlanDTO getWorkoutPlan(WorkoutPlanGetParams planGetParams) {
        UUID userID = planGetParams.getUserId();
        UUID trainerId = planGetParams.getTrainerId();

        LocalDate currentDate = planGetParams.getDate();

        // Verify if the day is correct
        DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
        System.out.println("Selected date: " + currentDate + " Day of the week: " + dayOfWeek);

        if (dayOfWeek == DayOfWeek.SUNDAY) {
            return WorkoutPlanDTO.builder()
                    .message("Today is a rest day. No workouts are scheduled.")
                    .statusCode(HttpStatus.OK.value())
                    .build();
        }

        Optional<List<WorkoutPlan>> optionalWorkoutPlans = workoutPlanRepository.findByUserIdAndTrainerId(userID, trainerId);
        if (optionalWorkoutPlans.isPresent()) {
            List<WorkoutPlan> workoutPlans = optionalWorkoutPlans.get();
            for (WorkoutPlan workoutPlan : workoutPlans) {
                LocalDate startDate = workoutPlan.getStartDate();
                LocalDate endDate = workoutPlan.getPlanExpire();

                // Validate start and end dates for correctness
                System.out.println("Workout start date: " + startDate + " end date: " + endDate);

                if (!currentDate.isBefore(startDate) && !currentDate.isAfter(endDate)) {
                    List<DailyWorkout> plansDailyWorkout = workoutPlan.getDailyWorkouts();

                    for (DailyWorkout dailyWorkout : plansDailyWorkout) {
                        if (dailyWorkout.getDay().equals(dayOfWeek.toString())) {
                            return WorkoutPlanDTO.builder()
                                    .todayWorkout(dailyWorkout)
                                    .workoutPlan(workoutPlan)
                                    .message("Today's workout is fetched successfully.")
                                    .statusCode(HttpStatus.OK.value())
                                    .build();
                        }
                    }
                }
            }
        }

        return WorkoutPlanDTO.builder()
                .message("Workout is not yet added by the trainer.")
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
    }

    @Override
    public List<WorkoutPlan> getTrainerWorkoutPlans(PlanReqParams planReqParams) {
        UUID userId = planReqParams.getUserId();
        UUID trainerId = planReqParams.getTrainerId();
        Optional<List<WorkoutPlan>> optionalWorkoutPlans = workoutPlanRepository.findByUserIdAndTrainerId(userId,trainerId);
        if(optionalWorkoutPlans.isPresent()){
            List<WorkoutPlan> workoutPlan = optionalWorkoutPlans.get();
            return workoutPlan;

        }
        return null;
    }

    @Override
    public CommonResponseDTO deleteWorkoutPlan(Long planId) {
        workoutPlanRepository.deleteById(planId);
        return CommonResponseDTO.builder()
                .message("Workout plan deleted successfully..")
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    @Override
    public CommonResponseDTO deleteDailyWorkout(Long planId) {
        dailyWorkoutRepository.deleteById(planId);
        return CommonResponseDTO.builder()
                .message("DailyWorkout  deleted successfully..")
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    @Override
    public CommonResponseDTO updateWorkoutPlan(UpdateWorkoutPlanReq updateWorkoutPlanReq) {
        long workoutPlanId = updateWorkoutPlanReq.getWorkoutPlanId();
        Optional<WorkoutPlan> optionalPlan = workoutPlanRepository.findById(workoutPlanId);

        if (!optionalPlan.isPresent()) {
            return CommonResponseDTO.builder()
                    .message("Workout plan not found.")
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .build();
        }

        WorkoutPlan existingPlan = optionalPlan.get();

        existingPlan.setPlanName(updateWorkoutPlanReq.getPlanName());
        existingPlan.setStartDate(updateWorkoutPlanReq.getStartDate());
        existingPlan.setRepeat(updateWorkoutPlanReq.getRepeat());
        existingPlan.setTrainerId(updateWorkoutPlanReq.getTrainerId());
        existingPlan.setDailyWorkouts(updateWorkoutPlanReq.getDailyWorkouts());
        existingPlan.setPlanExpire(updateWorkoutPlanReq.getStartDate().plusWeeks(updateWorkoutPlanReq.getRepeat()));

        workoutPlanRepository.save(existingPlan);

        return CommonResponseDTO.builder()
                .message("Workout plan updated successfully.")
                .statusCode(HttpStatus.OK.value())
                .build();


    }

    @Override
    public CommonResponseDTO updateDailyWorkout(UpdateDailyWorkout updateDailyWorkout) {
        long dailyWorkoutId = updateDailyWorkout.getDailyWorkoutId();
        Optional<DailyWorkout> optionalWorkout = dailyWorkoutRepository.findById(dailyWorkoutId);

        if (!optionalWorkout.isPresent()) {
            return CommonResponseDTO.builder()
                    .message("Daily workout not found.")
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .build();
        }

        DailyWorkout existingWorkout = optionalWorkout.get();

        existingWorkout.setDay(updateDailyWorkout.getDay());
        existingWorkout.setWorkoutType(updateDailyWorkout.getWorkoutType());

        List<Workout> updatedWorkouts = updateDailyWorkout.getWorkouts().stream().collect(Collectors.toList());
        double updatedTotalDuration = 0.0;
        double updatedTotalCaloriesBurned = 0.0;
        for (Workout workout : updatedWorkouts) {
            updatedTotalDuration += workout.getDurationMinutes();
            updatedTotalCaloriesBurned += workout.getCaloriesBurned();
        }

        existingWorkout.setWorkouts(updatedWorkouts);
        existingWorkout.setDuration(updatedTotalDuration);
        existingWorkout.setCaloriesBurned(updatedTotalCaloriesBurned);

        dailyWorkoutRepository.save(existingWorkout);

        return CommonResponseDTO.builder()
                .message("Daily workout updated successfully.")
                .statusCode(HttpStatus.OK.value())
                .build();

    }
}
