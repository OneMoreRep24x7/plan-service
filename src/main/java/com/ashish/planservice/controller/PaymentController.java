package com.ashish.planservice.controller;

import com.ashish.planservice.dto.PaymentData;
import com.ashish.planservice.dto.PaymentResponse;
import com.ashish.planservice.dto.TransactionDetails;
import com.ashish.planservice.model.AppPayment;
import com.ashish.planservice.service.PaymentService;
import com.razorpay.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/plans")
public class PaymentController {

    @Autowired
    PaymentService paymentService;
    @GetMapping("/createTransaction")
    public ResponseEntity<TransactionDetails> createTransaction(@RequestParam(name = "amount")Double amount){
        return ResponseEntity.ok(paymentService.createTransaction(amount));
    }

    @PostMapping("/saveAppPayment")
    public ResponseEntity<PaymentResponse> saveAppPayment(
            @RequestBody PaymentData paymentData
            ){

        return ResponseEntity.ok(paymentService.SaveAppPayment(paymentData));
    }

}
