package ayd.back.taller.dto.response.invoices;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class InvoiceResponseDto {
    private Integer id;
    private Integer clientId;
    private Integer jobId;
    private Integer issuedBy;
    private LocalDateTime issuedAt;
    private Double total;
    private String status;
    private List<InvoiceItemResponseDto> items;
}
