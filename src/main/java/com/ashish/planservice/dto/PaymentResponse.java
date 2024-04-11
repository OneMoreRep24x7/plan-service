package com.ashish.planservice.dto;

import com.ashish.planservice.model.AppPayment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PaymentResponse {
    private AppPayment payment;
    private String message;
    private int statusCode;
}
