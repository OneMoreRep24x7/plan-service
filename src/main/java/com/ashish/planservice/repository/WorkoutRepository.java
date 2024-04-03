package com.ashish.planservice.repository;

import com.ashish.planservice.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout,Long> {
    Optional<Workout> findByName(String name);
}
