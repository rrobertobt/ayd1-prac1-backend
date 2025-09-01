package ayd.back.taller.service;

import ayd.back.taller.dto.request.invoices.CreateInvoiceRequestDto;
import ayd.back.taller.dto.request.invoices.InvoiceItemRequestDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.dto.response.invoices.InvoiceItemResponseDto;
import ayd.back.taller.dto.response.invoices.InvoiceResponseDto;
import ayd.back.taller.exception.BusinessException;
import ayd.back.taller.repository.entities.InvoiceEntity;
import ayd.back.taller.repository.entities.InvoiceItemEntity;
import ayd.back.taller.repository.crud.UserCrud;
import ayd.back.taller.repository.crud.InvoiceRepository;
import ayd.back.taller.repository.crud.JobRepository;
import ayd.back.taller.repository.entities.UserEntity;
import ayd.back.taller.repository.enums.InvoiceStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final UserCrud userRepo;
    private final JobRepository jobRepository;
    private final SessionService sessionService;

    @Transactional
    public ResponseSuccessDto createInvoice(String token, CreateInvoiceRequestDto dto) {
        var adminSession = sessionService.isAdmin(token);

        UserEntity admin = userRepo.getUserByEmail(adminSession.getEmail())
                .orElseThrow( () -> new BusinessException(HttpStatus.NOT_FOUND, "Admin not found"));

        var client = userRepo.findById(dto.getClientId())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Client not found"));

        // Validar trabajo
        var job = jobRepository.findById(dto.getJobId())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Job not found"));

        // Crear entidad factura
        InvoiceEntity invoice = new InvoiceEntity();
        invoice.setClient(client);
        invoice.setJob(job);
        invoice.setIssuedBy(admin);
        invoice.setIssuedAt(LocalDateTime.now());
        invoice.setStatus(InvoiceStatusEnum.EMITIDO);
        // Calcular total
        double total = 0.0;
        for (InvoiceItemRequestDto itemDto : dto.getItems()) {
            double itemTotal = itemDto.getUnitPrice() * itemDto.getAmount();
            total += itemTotal;
        }
        invoice.setTotal(total);
        invoiceRepository.save(invoice);

        // Guardar items
        for (InvoiceItemRequestDto itemDto : dto.getItems()) {
            InvoiceItemEntity item = new InvoiceItemEntity();
            item.setInvoice(invoice);
            item.setDescription(itemDto.getDescription());
            item.setUnitPrice(itemDto.getUnitPrice());
            item.setAmount(itemDto.getAmount());
            invoice.getItems().add(item);
        }
        return ResponseSuccessDto.builder()
                .code(HttpStatus.CREATED)
                .message("Invoice created")
                .body(mapToResponse(invoiceRepository.save(invoice)))
                .build();
    }

    @Transactional(readOnly = true)
    public List<InvoiceResponseDto> getInvoicesByClient(String token, Integer clientId) {
        sessionService.validateSessionToken(token);
        return invoiceRepository.findByClientId(clientId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<InvoiceResponseDto> getInvoicesByDateRange(String token, LocalDate startDate, LocalDate endDate) {
        sessionService.validateSessionToken(token);
        return invoiceRepository.findByIssuedAtBetween(startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private InvoiceResponseDto mapToResponse(InvoiceEntity invoice) {
        List<InvoiceItemResponseDto> items = invoice.getItems().stream()
                .map(i -> new InvoiceItemResponseDto(
                        i.getId(),
                        i.getDescription(),
                        i.getUnitPrice(),
                        i.getAmount()
                ))
                .toList();

        return new InvoiceResponseDto(
                invoice.getId(),
                invoice.getClient().getId(),
                invoice.getJob().getId(),
                invoice.getIssuedBy().getId(),
                invoice.getIssuedAt(),
                invoice.getTotal(),
                invoice.getStatus().name(),
                items
        );
    }
}
