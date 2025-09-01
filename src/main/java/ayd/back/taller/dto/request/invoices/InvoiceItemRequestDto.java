package ayd.back.taller.dto.request.invoices;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InvoiceItemRequestDto {
    private String description;

    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.01", message = "Unit price must be greater than 0")
    private Double unitPrice;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.001", message = "Amount must be greater than 0")
    private Integer amount;
}
