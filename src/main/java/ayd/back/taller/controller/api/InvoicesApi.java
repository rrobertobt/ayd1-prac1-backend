package ayd.back.taller.controller.api;

import ayd.back.taller.dto.request.invoices.CreateInvoiceRequestDto;
import ayd.back.taller.dto.response.invoices.InvoiceResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/invoices")
public interface InvoicesApi {
    @Operation(
            summary = "Crear una nueva factura",
            description = "Crea una factura asociada a un cliente y un trabajo con listado de ítems."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Factura creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la petición"),
            @ApiResponse(responseCode = "401", description = "Token inválido o sesión expirada"),
            @ApiResponse(responseCode = "404", description = "Cliente o trabajo no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    InvoiceResponseDto createInvoice(
            @RequestHeader("session-token") String token,
            @RequestBody CreateInvoiceRequestDto request
    );

    @Operation(
            summary = "Obtener facturas por cliente",
            description = "Devuelve todas las facturas asociadas a un cliente dado su ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consulta exitosa"),
            @ApiResponse(responseCode = "401", description = "Token inválido o sesión expirada"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/client/{clientId}")
    List<InvoiceResponseDto> getInvoicesByClient(
            @RequestHeader("session-token") String token,
            @PathVariable Integer clientId
    );

    @Operation(
            summary = "Obtener facturas por rango de fechas",
            description = "Devuelve todas las facturas emitidas en un rango de fechas."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consulta exitosa"),
            @ApiResponse(responseCode = "400", description = "Fechas inválidas"),
            @ApiResponse(responseCode = "401", description = "Token inválido o sesión expirada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/date")
    List<InvoiceResponseDto> getInvoicesByDateRange(
            @RequestHeader("session-token") String token,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    );

}
