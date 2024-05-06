package com.ashish.planservice.configuration;

import com.ashish.planservice.dto.CaloriesBurnReqDTO;
import com.ashish.planservice.dto.CaloriesEatenReqDTO;
import com.ashish.planservice.dto.PaymentData;
import com.ashish.planservice.dto.TrainerPaymentData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "authority-service",url ="http://3.111.235.72:8081")
public interface AuthorityProxy {

    @PostMapping("/api/v1/user/updatePayment")
    public void updatePayment(@RequestBody PaymentData paymentData);
    @PostMapping("/api/v1/user/updateTrainerPayment")
    public void updateTrainerPayment(@RequestBody TrainerPaymentData paymentData);
    @PostMapping("/api/v1/tracking/updateCaloriesEaten")
    public void updateCaloriesEaten(@RequestBody CaloriesEatenReqDTO caloriesEatenReqDTO);
    @PostMapping("/api/v1/tracking/updateCaloriesBurned")
    public void updateCaloriesBurned(@RequestBody CaloriesBurnReqDTO caloriesBurnReqDTO);
}
