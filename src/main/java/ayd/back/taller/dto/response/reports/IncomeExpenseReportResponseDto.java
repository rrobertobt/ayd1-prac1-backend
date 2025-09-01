package ayd.back.taller.dto.response.reports;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class IncomeExpenseReportResponseDto {
    @Schema(description = "Total de ingresos en el periodo")
    private BigDecimal totalIncome;

    @Schema(description = "Total de egresos en el periodo")
    private BigDecimal totalExpenses;

    @Schema(description = "Balance neto (ingresos - egresos)")
    private BigDecimal netBalance;

    @Schema(description = "Listado de pagos incluidos (si aplica)")
    private List<PaymentItemDto> payments;

    @Schema(description = "Listado de pedidos incluidos (si aplica)")
    private List<PurchaseOrderItemDto> purchaseOrders;
}
