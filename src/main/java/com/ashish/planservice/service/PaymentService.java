package com.ashish.planservice.service;

import com.ashish.planservice.dto.PaymentData;
import com.ashish.planservice.dto.PaymentResponse;
import com.ashish.planservice.dto.TransactionDetails;

public interface PaymentService {
    TransactionDetails createTransaction(Double amount);

    PaymentResponse SaveAppPayment(PaymentData paymentData);
}
