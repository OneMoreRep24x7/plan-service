package com.ashish.planservice.repository;

import com.ashish.planservice.model.DailyWorkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DailyWorkoutRepository extends JpaRepository<DailyWorkout,Long> {
    Optional<List<DailyWorkout>> findByOwnerId(UUID ownerId);
}
