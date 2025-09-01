package ayd.back.taller.controller.api;


import ayd.back.taller.dto.request.invoices.CreatePaymentRequestDto;
import ayd.back.taller.dto.request.invoices.PaymentFilterRequestDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.dto.response.invoices.PaymentResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/payments")
public interface PaymentApi {

    @Operation(
            summary = "Registrar un nuevo pago",
            description = "Crea un nuevo pago asociado a una factura. Se validan saldo pendiente, existencia de la factura y el token de sesión."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pago registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o monto excede saldo pendiente"),
            @ApiResponse(responseCode = "401", description = "Token inválido o expirado"),
            @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    })
    @PostMapping
    ResponseEntity<ResponseSuccessDto> createPayment(
            @RequestHeader("session-token") String token,
            @RequestBody CreatePaymentRequestDto request
    );


    @Operation(
            summary = "Consultar historial de pagos",
            description = "Obtiene el listado de pagos filtrados por cliente, factura o rango de fechas. Requiere token válido."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consulta realizada exitosamente"),
            @ApiResponse(responseCode = "401", description = "Token inválido o expirado")
    })
    @GetMapping
    ResponseEntity<List<PaymentResponseDto>> getPayments(
            @RequestHeader("session-token") String token,
            PaymentFilterRequestDto filter
    );

}
