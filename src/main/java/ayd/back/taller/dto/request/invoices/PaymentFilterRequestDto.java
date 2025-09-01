package ayd.back.taller.dto.request.invoices;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentFilterRequestDto {
        private Long clientId;   // opcional
        private Long invoiceId;  // opcional
        private LocalDate from;  // opcional
        private LocalDate to;    // opcional

}
