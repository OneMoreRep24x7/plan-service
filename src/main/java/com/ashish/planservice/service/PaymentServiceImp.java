package com.ashish.planservice.service;

import com.ashish.planservice.configuration.AuthorityProxy;
import com.ashish.planservice.dto.PaymentData;
import com.ashish.planservice.dto.PaymentResponse;
import com.ashish.planservice.dto.TransactionDetails;
import com.ashish.planservice.model.AppPayment;
import com.ashish.planservice.repository.AppPaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.apache.catalina.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentServiceImp implements PaymentService{
    @Autowired
    private AppPaymentRepository appPaymentRepository;
    @Autowired
    private AuthorityProxy authorityProxy;
    private static final String KEY_ID = "rzp_test_Wzz9O0iUUIq7aa";
    private static final String KEY_SECRET = "uVcdgSkBCQj9q6fRwVGSwy9a";
    private static final String CURRENCY = "INR";
    @Override
    public TransactionDetails createTransaction(Double amount) {
        //razor pay always consider the smallest unit of currency
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("amount",(amount*100));
            jsonObject.put("currency",CURRENCY);
            RazorpayClient razorpayClient = new RazorpayClient(KEY_ID,KEY_SECRET);
            Order order = razorpayClient.orders.create(jsonObject);
            return prepareTransactionDetails(order);
        } catch (RazorpayException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @Override
    public PaymentResponse SaveAppPayment(PaymentData paymentData) {
        UUID userID = paymentData.getUserId();
        AppPayment payment = AppPayment.builder()
                .paymentId(paymentData.getPayment_id())
                .userId(userID)
                .amount(paymentData.getAmount())
                .build();
        AppPayment savedPayment = appPaymentRepository.save(payment);
        authorityProxy.updatePayment(paymentData);
        return PaymentResponse.builder()
                .payment(savedPayment)
                .message("Payment saved successfully..")
                .statusCode(HttpStatus.OK.value())
                .build();

    }

    private TransactionDetails prepareTransactionDetails(Order order){
        String orderId = order.get("id");
        String currency = order.get("currency");
        Integer amount = order.get("amount");

        TransactionDetails transactionDetails = TransactionDetails.builder()
                .orderId(orderId)
                .currency(currency)
                .amount(amount)
                .key(KEY_ID)
                .build();
        return transactionDetails;
    }
}
