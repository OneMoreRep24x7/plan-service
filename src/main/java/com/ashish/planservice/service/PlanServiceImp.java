package com.ashish.planservice.service;

import com.ashish.planservice.dto.CommonResponseDTO;
import com.ashish.planservice.dto.DailyWorkoutParams;
import com.ashish.planservice.dto.WorkoutPlanGetParams;
import com.ashish.planservice.dto.WorkoutPlanParams;
import com.ashish.planservice.model.DailyWorkout;
import com.ashish.planservice.model.Workout;
import com.ashish.planservice.model.WorkoutPlan;
import com.ashish.planservice.repository.DailyWorkoutRepository;
import com.ashish.planservice.repository.WorkoutPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlanServiceImp implements PlanService{
    @Autowired
    private DailyWorkoutRepository dailyWorkoutRepository;
    @Autowired
    private WorkoutPlanRepository workoutPlanRepository;

    @Override
    public CommonResponseDTO addDailyWorkout(DailyWorkoutParams dailyWorkoutParams) {
        System.out.println(dailyWorkoutParams+">>>>>>>>>");
        List<Workout> workouts = dailyWorkoutParams.getWorkouts();

        double totalDuration = 0.0;
        double totalCaloriesBurned = 0.0;
        for (Workout workout : workouts) {
            totalDuration += workout.getDurationMinutes();
            totalCaloriesBurned += workout.getCaloriesBurned();


        }

//        DailyWorkout dailyWorkout = DailyWorkout.builder()
//                .day(dailyWorkoutParams.getDay())
//                .workoutType(dailyWorkoutParams.getWorkoutType())
//                .workouts(workouts)
//                .ownerId(dailyWorkoutParams.getOwnerId())
//                .duration(totalDuration)
//                .caloriesBurned(totalCaloriesBurned)
//                .build();

        DailyWorkout dailyWorkout = new DailyWorkout();
        dailyWorkout.setDay(dailyWorkoutParams.getDay());
        dailyWorkout.setWorkoutType(dailyWorkoutParams.getWorkoutType());
        dailyWorkout.setWorkouts(dailyWorkoutParams.getWorkouts());
        dailyWorkout.setOwnerId(dailyWorkoutParams.getOwnerId());
        dailyWorkout.setDuration(totalDuration);
        dailyWorkout.setCaloriesBurned(totalCaloriesBurned);
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

        UUID userId = workoutPlanParams.getUserID();
        LocalDate date = workoutPlanParams.getDate();
        int week = workoutPlanParams.getWeek();
        int month = date.getMonthValue();
        Optional<List<WorkoutPlan>> optionalWorkoutPlans = workoutPlanRepository.findByUserId(userId);
        if (optionalWorkoutPlans.isPresent()) {
            List<WorkoutPlan> workoutPlans = optionalWorkoutPlans.get();
            for (WorkoutPlan plan : workoutPlans) {
                if (plan.getDate().getMonthValue() == month && plan.getWeek() == week) {
                    return CommonResponseDTO.builder()
                            .message("Workout plan already exists for this week in the same month.")
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build();
                }
            }
        }

        WorkoutPlan workoutPlan = WorkoutPlan.builder()
                .planName(workoutPlanParams.getPlanName())
                .date(workoutPlanParams.getDate())
                .week(workoutPlanParams.getWeek())
                .trainerId(workoutPlanParams.getTrainerId())
                .userId(workoutPlanParams.getUserID())
                .dailyWorkouts(workoutPlanParams.getDailyWorkouts())
                .build();

        workoutPlanRepository.save(workoutPlan);
        return CommonResponseDTO.builder()
                .message("Workout plan added successfully")
                .statusCode(HttpStatus.OK.value())
                .build();
    }


    @Override
    public WorkoutPlan getWorkoutPlan(WorkoutPlanGetParams planGetParams) {
        UUID userID = planGetParams.getUserId();
        LocalDate date = planGetParams.getDate();
        int week = planGetParams.getWeek();
        int month = date.getMonthValue();
        Optional<List<WorkoutPlan>> optionalWorkoutPlans = workoutPlanRepository.findByUserId(userID);
        if(optionalWorkoutPlans.isPresent()){
            List<WorkoutPlan> workoutPlans = optionalWorkoutPlans.get();
            for(WorkoutPlan workoutPlan : workoutPlans){
                if( workoutPlan.getDate().getMonthValue() == month && workoutPlan.getWeek() == week){
                    return  workoutPlan;
                }
            }

        }
        return null;
    }
}
