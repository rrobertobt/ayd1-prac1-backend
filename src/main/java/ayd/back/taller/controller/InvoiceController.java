package ayd.back.taller.controller;

import ayd.back.taller.controller.api.InvoicesApi;
import ayd.back.taller.dto.request.invoices.CreateInvoiceRequestDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.dto.response.invoices.InvoiceResponseDto;
import ayd.back.taller.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class InvoiceController implements InvoicesApi {

    private static final Logger log = LoggerFactory.getLogger(JobPartsController.class);
    private final InvoiceService invoiceService;

    @Override
    public ResponseEntity<ResponseSuccessDto> createInvoice(String token, CreateInvoiceRequestDto request) {
        log.info("POST /invoices");
        return ResponseEntity.ok( invoiceService.createInvoice(token, request) );
    }

    @Override
    public ResponseEntity<List<InvoiceResponseDto>> getInvoicesByClient(String token, Integer clientId) {
        log.info("GET /invoices/client/{clientId}");
        return ResponseEntity.ok( invoiceService.getInvoicesByClient(token, clientId) );
    }

    @Override
    public ResponseEntity<List<InvoiceResponseDto>> getInvoicesByDateRange(String token, LocalDate startDate, LocalDate endDate) {
        log.info("GET /invoices/date");
        return ResponseEntity.ok( invoiceService.getInvoicesByDateRange(token, startDate, endDate) );
    }
}
