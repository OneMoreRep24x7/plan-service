package com.ashish.planservice.service;

import com.ashish.planservice.dto.*;

import java.util.UUID;

public interface PaymentService {
    TransactionDetails createTransaction(Double amount);

    PaymentResponse saveAppPayment(PaymentData paymentData);

    TrainerPaymentResponse saveTrainerPayment(TrainerPaymentData paymentData);


}
