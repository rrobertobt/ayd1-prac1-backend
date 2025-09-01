package ayd.back.taller.dto.response.reports;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PaymentItemDto {
    @Schema(description = "ID del pago")
    private Integer id;

    @Schema(description = "ID de la factura asociada (puede ser null)")
    private Integer invoiceId;

    @Schema(description = "Monto del pago")
    private BigDecimal amount;

    @Schema(description = "Método de pago")
    private String method;

    @Schema(description = "Fecha/hora del pago")
    private LocalDateTime paidAt;
}
