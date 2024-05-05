package com.ashish.planservice.repository;

import com.ashish.planservice.model.AppPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppPaymentRepository extends JpaRepository<AppPayment,Long> {
}
