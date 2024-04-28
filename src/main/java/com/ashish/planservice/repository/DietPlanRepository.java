package com.ashish.planservice.repository;

import com.ashish.planservice.model.DietPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DietPlanRepository extends JpaRepository<DietPlan,Long> {
    Optional<List<DietPlan>> findByUserId(UUID userId);
    Optional<List<DietPlan>> findByUserIdAndTrainerId(UUID userId, UUID trainerId);
}
