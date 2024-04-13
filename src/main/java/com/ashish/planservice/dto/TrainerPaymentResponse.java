package com.ashish.planservice.dto;

import com.ashish.planservice.model.AppPayment;
import com.ashish.planservice.model.TrainerPayment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TrainerPaymentResponse {
    private TrainerPayment payment;
    private String message;
    private int statusCode;
}
