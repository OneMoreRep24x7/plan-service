package com.ashish.planservice.repository;

import com.ashish.planservice.model.TrainerPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TrainerPaymentRepository extends JpaRepository<TrainerPayment,Long> {
    UUID findTrainerIdByUserId(UUID userId);
}
