package ayd.back.taller.dto.response.reports;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PurchaseOrderItemDto {
    @Schema(description = "ID del pedido")
    private Integer id;

    @Schema(description = "ID del proveedor")
    private Integer supplierId;

    @Schema(description = "Estado del pedido")
    private String status;

    @Schema(description = "Total del pedido")
    private BigDecimal total;

    @Schema(description = "Fecha del pedido")
    private LocalDate orderDate;

    @Schema(description = "Fecha creación del registro")
    private LocalDateTime createdAt;
}
