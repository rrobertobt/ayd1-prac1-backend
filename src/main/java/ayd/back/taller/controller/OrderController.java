package ayd.back.taller.controller;

import ayd.back.taller.controller.api.OrderApi;
import ayd.back.taller.dto.request.orders.CreateOrderRequestDto;
import ayd.back.taller.dto.request.orders.UpdateDeliveryDateRequestDto;
import ayd.back.taller.dto.request.orders.UpdateOrderStatusRequestDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.repository.enums.PurchaseStatusEnum;
import ayd.back.taller.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OrderController implements OrderApi {

    private final OrderService service;

    @Override
    public ResponseEntity<ResponseSuccessDto> create(CreateOrderRequestDto dto, String token) {
        log.info("POST /orders");
        var resp = service.create(dto, token);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessDto> list(PurchaseStatusEnum status, String token) {
        log.info("GET /orders?status={}", status);
        var resp = service.list(status, token);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessDto> updateStatus(Integer id, UpdateOrderStatusRequestDto dto, String token) {
        log.info("PATCH /orders/{}/status -> {}", id, dto.getStatus());
        var resp = service.updateStatus(id, dto, token);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessDto> updateDeliveryDate(Integer id, UpdateDeliveryDateRequestDto dto, String token) {
        log.info("PATCH /orders/{}/delivery-date -> {}", id, dto.getDeliveryDate());
        var resp = service.updateDeliveryDate(id, dto, token);
        return new ResponseEntity<>(resp, resp.getCode());
    }
}

