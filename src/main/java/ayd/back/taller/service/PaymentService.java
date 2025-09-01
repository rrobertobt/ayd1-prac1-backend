package ayd.back.taller.service;

import ayd.back.taller.dto.request.invoices.CreatePaymentRequestDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.dto.response.invoices.PaymentResponseDto;
import ayd.back.taller.dto.request.invoices.PaymentFilterRequestDto;
import ayd.back.taller.exception.BusinessException;
import ayd.back.taller.repository.entities.InvoiceEntity;
import ayd.back.taller.repository.entities.PaymentEntity;
import ayd.back.taller.repository.crud.InvoiceRepository;
import ayd.back.taller.repository.crud.PaymentRepository;
import ayd.back.taller.repository.entities.UserEntity;
import ayd.back.taller.repository.enums.PaymentMethodEnum;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {
    @PersistenceContext
    private final EntityManager em;

    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;
    private final SessionService sessionService;

    @Transactional
    public ResponseSuccessDto createPayment(CreatePaymentRequestDto dto, String token) {
        sessionService.isAdmin(token);

        InvoiceEntity invoice = invoiceRepository.findById(dto.getInvoiceId())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Factura no encontrada"));

        // Validación: monto no mayor al saldo pendiente
        Double totalPagado = paymentRepository.sumPaymentsByInvoice(invoice.getId());
        Double saldoPendiente = invoice.getTotal() - totalPagado;

        if (dto.getAmount().compareTo(saldoPendiente) > 0) {
            throw new BusinessException(HttpStatus.CONFLICT, "El monto excede el saldo pendiente de la factura");
        }

        PaymentEntity payment = new PaymentEntity();
        payment.setInvoice(invoice);
        payment.setAmount(dto.getAmount());
        payment.setMethod(PaymentMethodEnum.valueOf(dto.getMethod().toUpperCase()));
        payment.setPaidAt(LocalDateTime.now());
        payment.setCreatedAt(LocalDateTime.now());

        paymentRepository.save(payment);

        return ResponseSuccessDto.builder()
                .code(HttpStatus.CREATED)
                .message("Invoice created")
                .body(toDto(payment))
                .build();
    }

    public List<PaymentResponseDto> getPayments(PaymentFilterRequestDto filter, String token) {
        sessionService.isAdmin(token);

        List<PaymentEntity> payments = findPaymentsByFilters(
                filter.getClientId(),
                filter.getInvoiceId(),
                filter.getFrom(),
                filter.getTo()
        );

        return payments.stream().map(this::toDto).collect(Collectors.toList());
    }

    private PaymentResponseDto toDto(PaymentEntity entity) {
        PaymentResponseDto dto = new PaymentResponseDto();
        dto.setId(entity.getId());
        dto.setInvoiceId(entity.getInvoice().getId());
        dto.setAmount(entity.getAmount());
        dto.setMethod(entity.getMethod().name());
        dto.setPaidAt(entity.getPaidAt());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    private List<PaymentEntity> findPaymentsByFilters(Long clientId, Long invoiceId, LocalDate from, LocalDate to) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PaymentEntity> cq = cb.createQuery(PaymentEntity.class);
        Root<PaymentEntity> payment = cq.from(PaymentEntity.class);
        Join<PaymentEntity, InvoiceEntity> invoice = payment.join("invoice");
        Join<InvoiceEntity, UserEntity> client = invoice.join("client");

        List<Predicate> predicates = new ArrayList<>();

        if (clientId != null) predicates.add(cb.equal(client.get("id"), clientId));
        if (invoiceId != null) predicates.add(cb.equal(invoice.get("id"), invoiceId));
        if (from != null) predicates.add(cb.greaterThanOrEqualTo(payment.get("paidAt"), from.atStartOfDay()));
        if (to != null) predicates.add(cb.lessThanOrEqualTo(payment.get("paidAt"), to.atStartOfDay()));

        cq.where(predicates.toArray(new Predicate[0]));
        return em.createQuery(cq).getResultList();
    }

}
