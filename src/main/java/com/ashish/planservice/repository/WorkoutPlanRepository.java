package com.ashish.planservice.repository;

import com.ashish.planservice.model.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan,Long> {
    Optional<List<WorkoutPlan>> findByUserId(UUID userID);
}
