package com.ashish.planservice.configuration;

import com.ashish.planservice.dto.PaymentData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "authority-service")
public interface AuthorityProxy {

    @PostMapping("/api/v1/user/updatePayment")
    public void updatePayment(@RequestBody PaymentData paymentData);
}
