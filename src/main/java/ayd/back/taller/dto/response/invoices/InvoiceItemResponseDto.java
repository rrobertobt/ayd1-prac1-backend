package ayd.back.taller.dto.response.invoices;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvoiceItemResponseDto {
    private Integer id;
    private String description;
    private Double unitPrice;
    private Integer amount;
}
