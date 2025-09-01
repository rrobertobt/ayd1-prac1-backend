package ayd.back.taller.dto.response.invoices;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentResponseDto {
    private Integer id;
    private Integer invoiceId;
    private Double amount;
    private String method;
    private LocalDateTime paidAt;
    private LocalDateTime createdAt;

}
