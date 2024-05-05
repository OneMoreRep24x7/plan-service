package com.ashish.planservice.repository;

import com.ashish.planservice.model.DailyDiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DailyDietRepository extends JpaRepository<DailyDiet,Long> {
    Optional<List<DailyDiet>> findByOwnerId(UUID ownerId);
}
