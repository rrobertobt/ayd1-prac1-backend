package ayd.back.taller.controller;

import ayd.back.taller.controller.api.InvoicesApi;
import ayd.back.taller.dto.request.invoices.CreateInvoiceRequestDto;
import ayd.back.taller.dto.response.invoices.InvoiceResponseDto;
import ayd.back.taller.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class InvoiceController implements InvoicesApi {

    private final InvoiceService invoiceService;

    @Override
    public InvoiceResponseDto createInvoice(String token, CreateInvoiceRequestDto request) {
        return invoiceService.createInvoice(token, request);
    }

    @Override
    public List<InvoiceResponseDto> getInvoicesByClient(String token, Integer clientId) {
        return invoiceService.getInvoicesByClient(token, clientId);
    }

    @Override
    public List<InvoiceResponseDto> getInvoicesByDateRange(String token, LocalDate startDate, LocalDate endDate) {
        return invoiceService.getInvoicesByDateRange(token, startDate, endDate);
    }
}
