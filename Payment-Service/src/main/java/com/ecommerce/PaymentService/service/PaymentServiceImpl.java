package com.ecommerce.PaymentService.service;

import com.ecommerce.PaymentService.entity.TransactionDetails;
import com.ecommerce.PaymentService.model.PaymentRequest;
import com.ecommerce.PaymentService.repository.TransactionDetailsRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;
    @Override
    public long doPayment(PaymentRequest paymentRequest) {
       log.info("Recording payment details:{}",paymentRequest);

        TransactionDetails transactionDetails = TransactionDetails.builder()
                .amount(paymentRequest.getAmount())
                .paymentDate(Instant.now())
                .paymentStatus("SUCCESS")
                .orderId(paymentRequest.getOrderId())
                .referenceNumber(paymentRequest.getReferenceNumber())
                .paymentMode(paymentRequest.getPaymentMode().name())
                .build();

        TransactionDetails transactionDetails1 = transactionDetailsRepository.save(transactionDetails);
        log.info("Transaction completed {}",transactionDetails1);
        return transactionDetails1.getId();
    }
}
