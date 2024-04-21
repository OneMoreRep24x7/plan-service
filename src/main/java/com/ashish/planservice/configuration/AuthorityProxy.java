package com.ashish.planservice.configuration;

import com.ashish.planservice.dto.PaymentData;
import com.ashish.planservice.dto.TrainerPaymentData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "authority-service")
public interface AuthorityProxy {

    @PostMapping("/api/v1/user/updatePayment")
    public void updatePayment(@RequestBody PaymentData paymentData);
    @PostMapping("/api/v1/user/updateTrainerPayment")
    public void updateTrainerPayment(@RequestBody TrainerPaymentData paymentData);
}