package ayd.back.taller.controller.api;

import ayd.back.taller.dto.request.orders.CreateOrderRequestDto;
import ayd.back.taller.dto.request.orders.UpdateDeliveryDateRequestDto;
import ayd.back.taller.dto.request.orders.UpdateOrderStatusRequestDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.repository.enums.PurchaseStatusEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/orders")
public interface OrderApi {

    @Operation(
            summary = "Crear pedido de repuestos",
            description = "El administrador crea un pedido con sus ítems y calcula el total",
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "Pedido creado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseSuccessDto.class)))
            }
    )
    @PostMapping
    ResponseEntity<ResponseSuccessDto> create(@RequestBody CreateOrderRequestDto dto, @RequestHeader(value = "session-token") String token);

    @Operation(
            summary = "Listar pedidos",
            description = "Si es administrador consulta el historial completo de pedidos con filtro opcional por estado. Si es proveedor consulta el historial de pedidos recibidos con filtro opcional por estado"
    )
    @GetMapping
    ResponseEntity<ResponseSuccessDto> list(
            @Parameter(description = "Estado del pedido para filtrar (opcional)") @RequestParam(value = "status", required = false) PurchaseStatusEnum status,
            @RequestHeader(value = "session-token") String token
    );

    @Operation(
            summary = "Modificar estado del pedido",
            description = "Un proveedor actualiza el estado de uno de sus pedidos de compra"
    )
    @PatchMapping("/{id}/status")
    ResponseEntity<ResponseSuccessDto> updateStatus(
            @PathVariable Integer id,
            @RequestBody UpdateOrderStatusRequestDto dto,
            @RequestHeader(value = "session-token") String token
    );

    @Operation(
            summary = "Cambiar fecha de entrega",
            description = "Actualiza la fecha de entrega del pedido"
    )
    @PatchMapping("/{id}/delivery-date")
    ResponseEntity<ResponseSuccessDto> updateDeliveryDate(
            @PathVariable Integer id,
            @RequestBody UpdateDeliveryDateRequestDto dto,
            @RequestHeader(value = "session-token") String token
    );
}
