package ayd.back.taller.dto.request.invoices;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateInvoiceRequestDto {
    @NotNull(message = "Client ID is required")
    Integer clientId;

    @NotNull(message = "Job ID is required")
    Integer jobId;

    String description;

    @NotEmpty(message = "Invoice must have at least one item")
    List<InvoiceItemRequestDto> items;
}
