package ayd.back.taller.controller;

import ayd.back.taller.controller.api.PaymentApi;
import ayd.back.taller.dto.request.invoices.CreatePaymentRequestDto;
import ayd.back.taller.dto.request.invoices.PaymentFilterRequestDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.dto.response.invoices.PaymentResponseDto;
import ayd.back.taller.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PaymentController implements PaymentApi {

    private static final Logger log = LoggerFactory.getLogger(JobPartsController.class);
    private final PaymentService paymentService;

    @Override
    public ResponseEntity<ResponseSuccessDto> createPayment(String token, CreatePaymentRequestDto request) {
        log.info("POST /payments");
        return ResponseEntity.ok( paymentService.createPayment(request, token) );
    }

    @Override
    public ResponseEntity<List<PaymentResponseDto>> getPayments(String token, PaymentFilterRequestDto filter) {
        log.info("GET /payments");
        return ResponseEntity.ok( paymentService.getPayments(filter, token) );
    }
}
