package ayd.back.taller.dto.request.invoices;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePaymentRequestDto {
    @NotNull(message = "El ID de la factura es obligatorio")
    private Integer invoiceId;

    @NotNull(message = "El monto del pago es obligatorio")
    @Min(value = 1, message = "El monto debe ser mayor a 0")
    private Double amount;

    private String method = "EFECTIVO";
}
